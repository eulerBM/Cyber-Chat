import { useState } from "react";
import { AuthLayout } from "@/components/auth/AuthLayout";
import { LoginForm } from "@/components/auth/LoginForm";
import { RegisterForm } from "@/components/auth/RegisterForm";

interface AuthProps {
  onAuthenticated: (user: { name: string; email: string }) => void;
}

export default function Auth({ onAuthenticated }: AuthProps) {
  const [isLogin, setIsLogin] = useState(true);

  const handleLogin = (email: string, password: string) => {
    onAuthenticated({ name: "Usuário", email });
  };

  const handleRegister = (name: string, email: string, password: string) => {
    onAuthenticated({ name, email });
  };

  return (
    <AuthLayout
      title={isLogin ? "Bem-vindo" : "Criar Conta"}
      subtitle={isLogin ? "Entre na sua conta" : "Cadastre-se para começar"}
    >
      {isLogin ? (
        <LoginForm
          onSwitchToRegister={() => setIsLogin(false)}
          onLogin={handleLogin}
        />
      ) : (
        <RegisterForm
          onSwitchToLogin={() => setIsLogin(true)}
          onRegister={handleRegister}
        />
      )}
    </AuthLayout>
  );
}