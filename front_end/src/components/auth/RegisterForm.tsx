import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Mail, Lock, User, ArrowRight } from "lucide-react";

interface RegisterFormProps {
  onSwitchToLogin: () => void;
  onRegister: (name: string, email: string, password: string) => void;
}

export function RegisterForm({ onSwitchToLogin, onRegister }: RegisterFormProps) {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setSuccess(null);

    if (password !== confirmPassword) {
      setError("As senhas não coincidem!");
      return;
    }

    var passwordConfirm = confirmPassword;

    const payload = { name, email, password, passwordConfirm};
    setLoading(true);

    try {
      const response = await fetch("http://localhost:8080/auth/users/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (!response.ok) {
        // tenta pegar mensagem do backend, se houver
        let msg = "Não foi possível registrar";
        try {

          const json = await response.json();

          console.log(json)
          
          msg = json?.content || JSON.stringify(json) || msg;
        } catch {
          const text = await response.text().catch(() => "");
          if (text) msg = text;
        }
        setError(msg);
        return;
      }

      // sucesso
      const data = await response.json().catch(() => null);
      setSuccess("Cadastro realizado com sucesso!");
      onRegister(name, email, password);

      // opcional: limpar campos
      setName("");
      setEmail("");
      setPassword("");
      setConfirmPassword("");
    } catch (err) {
      console.error(err);
      setError("Erro na conexão com o servidor.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      {error && <p className="text-sm text-red-500">{error}</p>}
      {success && <p className="text-sm text-green-500">{success}</p>}

      <div className="space-y-2">
        <Label htmlFor="name" className="text-foreground/90">Nome</Label>
        <div className="relative">
          <User className="absolute left-3 top-1/2 transform -translate-y-1/2 text-primary w-4 h-4" />
          <Input
            id="name"
            type="text"
            placeholder="Seu nome"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="pl-10 bg-input/50 border-primary/30 focus:border-primary cyber-glow"
            required
          />
        </div>
      </div>

      <div className="space-y-2">
        <Label htmlFor="email" className="text-foreground/90">Email</Label>
        <div className="relative">
          <Mail className="absolute left-3 top-1/2 transform -translate-y-1/2 text-primary w-4 h-4" />
          <Input
            id="email"
            type="email"
            placeholder="seu@email.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="pl-10 bg-input/50 border-primary/30 focus:border-primary cyber-glow"
            required
          />
        </div>
      </div>

      <div className="space-y-2">
        <Label htmlFor="password" className="text-foreground/90">Senha</Label>
        <div className="relative">
          <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 text-primary w-4 h-4" />
          <Input
            id="password"
            type="password"
            placeholder="••••••••"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="pl-10 bg-input/50 border-primary/30 focus:border-primary cyber-glow"
            required
          />
        </div>
      </div>

      <div className="space-y-2">
        <Label htmlFor="confirmPassword" className="text-foreground/90">Confirmar Senha</Label>
        <div className="relative">
          <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 text-primary w-4 h-4" />
          <Input
            id="confirmPassword"
            type="password"
            placeholder="••••••••"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            className="pl-10 bg-input/50 border-primary/30 focus:border-primary cyber-glow"
            required
          />
        </div>
      </div>

      <Button type="submit" variant="laser" className="w-full group" disabled={loading}>
        {loading ? (
          <>
            Cadastrando...
            <ArrowRight className="w-4 h-4 ml-2 animate-[spin_1s_linear_infinite]" />
          </>
        ) : (
          <>
            Cadastrar
            <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform ml-2" />
          </>
        )}
      </Button>

      <div className="text-center">
        <p className="text-muted-foreground">
          Já tem uma conta?{" "}
          <button
            type="button"
            onClick={onSwitchToLogin}
            className="text-primary hover:text-primary-glow underline-offset-4 hover:underline transition-colors"
          >
            Entrar
          </button>
        </p>
      </div>
    </form>
  );
}
