import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Mail, Lock, ArrowRight } from "lucide-react";

interface LoginFormProps {
  onSwitchToRegister: () => void;
  onLogin: (email: string, password: string) => void;
}

export function LoginForm({ onSwitchToRegister, onLogin }: LoginFormProps) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onLogin(email, password);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <div className="space-y-4">
        <div className="space-y-2 slide-in-left">
          <Label htmlFor="email" className="text-foreground/90 text-sm font-medium">Email</Label>
          <div className="relative group">
            <Mail className="absolute left-3 top-1/2 transform -translate-y-1/2 text-primary w-4 h-4 transition-colors group-focus-within:text-primary-glow" />
            <Input
              id="email"
              type="email"
              placeholder="seu@email.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="pl-10 h-12 glass-effect border-primary/30 focus:border-primary focus:ring-2 focus:ring-primary/20 cyber-glow transition-all duration-300"
              required
            />
          </div>
        </div>

        <div className="space-y-2 slide-in-left" style={{ animationDelay: '0.1s' }}>
          <Label htmlFor="password" className="text-foreground/90 text-sm font-medium">Senha</Label>
          <div className="relative group">
            <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 text-primary w-4 h-4 transition-colors group-focus-within:text-primary-glow" />
            <Input
              id="password"
              type="password"
              placeholder="••••••••"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="pl-10 h-12 glass-effect border-primary/30 focus:border-primary focus:ring-2 focus:ring-primary/20 cyber-glow transition-all duration-300"
              required
            />
          </div>
        </div>
      </div>

      <div className="pt-2">
        <Button type="submit" variant="laser" className="w-full h-12 text-lg font-medium group hover-lift" style={{ animationDelay: '0.2s' }}>
          <span className="relative z-10">Entrar</span>
          <ArrowRight className="w-5 h-5 ml-2 group-hover:translate-x-1 transition-transform" />
        </Button>
      </div>

      <div className="text-center pt-4 fade-in-up" style={{ animationDelay: '0.3s' }}>
        <p className="text-muted-foreground">
          Não tem uma conta?{" "}
          <button
            type="button"
            onClick={onSwitchToRegister}
            className="text-primary hover:text-primary-glow underline-offset-4 hover:underline transition-all duration-300 font-medium relative group"
          >
            Cadastre-se
            <span className="absolute inset-x-0 -bottom-1 h-0.5 bg-gradient-laser scale-x-0 group-hover:scale-x-100 transition-transform duration-300 origin-left" />
          </button>
        </p>
      </div>
    </form>
  );
}