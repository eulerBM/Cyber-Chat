import { ReactNode } from "react";

interface AuthLayoutProps {
  children: ReactNode;
  title: string;
  subtitle: string;
}

export function AuthLayout({ children, title, subtitle }: AuthLayoutProps) {
  return (
    <div className="min-h-screen flex items-center justify-center p-4 relative">
      {/* Floating Elements */}
      <div className="absolute top-1/4 left-1/4 w-32 h-32 rounded-full bg-gradient-laser opacity-10 blur-3xl floating" />
      <div className="absolute bottom-1/4 right-1/4 w-48 h-48 rounded-full bg-gradient-glow opacity-20 blur-2xl floating" style={{ animationDelay: '-3s' }} />
      
      <div className="w-full max-w-md relative fade-in-up">
        <div className="glass-effect cyber-glow laser-border p-8 rounded-lg hover-lift relative overflow-hidden">
          {/* Inner glow effect */}
          <div className="absolute inset-0 bg-gradient-glow opacity-5 pointer-events-none" />
          
          <div className="text-center mb-8 relative z-10">
            <div className="mb-4">
              <div className="w-16 h-16 mx-auto rounded-full bg-gradient-laser flex items-center justify-center pulse-glow">
                <div className="w-8 h-8 rounded-full bg-background flex items-center justify-center">
                  <div className="w-4 h-4 rounded-full bg-gradient-laser" />
                </div>
              </div>
            </div>
            <h1 className="text-4xl font-bold bg-gradient-laser bg-clip-text text-transparent mb-3 tracking-tight">
              {title}
            </h1>
            <p className="text-muted-foreground text-lg">{subtitle}</p>
          </div>
          {children}
        </div>
      </div>
    </div>
  );
}