import { useState } from "react";
import { Button } from "@/components/ui/button";
import { UserSearch } from "@/components/chat/UserSearch";
import { ChatInterface } from "@/components/chat/ChatInterface";
import { LogOut, MessageSquare } from "lucide-react";

interface User {
  id: string;
  name: string;
  email: string;
  avatar?: string;
}

interface DashboardProps {
  currentUser: { name: string; email: string };
  onLogout: () => void;
}

export default function Dashboard({ currentUser, onLogout }: DashboardProps) {
  const [selectedUser, setSelectedUser] = useState<User | null>(null);

  return (
    <div className="min-h-screen p-6 relative">
      {/* Background Elements */}
      <div className="absolute top-10 right-10 w-64 h-64 rounded-full bg-gradient-glow opacity-10 blur-3xl floating" />
      <div className="absolute bottom-20 left-10 w-48 h-48 rounded-full bg-gradient-laser opacity-5 blur-2xl floating" style={{ animationDelay: '-4s' }} />
      
      <div className="max-w-6xl mx-auto relative">
        {/* Enhanced Header */}
        <div className="glass-effect cyber-glow laser-border p-8 rounded-lg mb-8 hover-lift fade-in-up relative overflow-hidden">
          <div className="absolute inset-0 mesh-gradient opacity-5 pointer-events-none" />
          
          <div className="flex justify-between items-center relative z-10">
            <div className="flex items-center space-x-6">
              <div className="relative">
                <div className="w-16 h-16 rounded-full bg-gradient-laser flex items-center justify-center pulse-glow">
                  <MessageSquare className="w-8 h-8 text-primary-foreground" />
                </div>
                <div className="absolute -top-1 -right-1 w-6 h-6 bg-green-500 rounded-full border-2 border-background flex items-center justify-center">
                  <div className="w-2 h-2 bg-white rounded-full pulse-glow" />
                </div>
              </div>
              
              <div>
                <h1 className="text-3xl font-bold bg-gradient-laser bg-clip-text text-transparent tracking-tight">
                  Cyber Chat
                </h1>
                <p className="text-muted-foreground text-lg">
                  Bem-vindo, <span className="text-primary font-semibold">{currentUser.name}</span>
                </p>
                <div className="flex items-center space-x-2 mt-1">
                  <div className="w-2 h-2 bg-green-500 rounded-full pulse-glow" />
                  <span className="text-sm text-muted-foreground">Online</span>
                </div>
              </div>
            </div>
            
            <Button variant="outline" onClick={onLogout} className="group hover-lift">
              <LogOut className="w-4 h-4 mr-2 group-hover:scale-110 transition-transform" />
              Sair da Conta
            </Button>
          </div>
        </div>

        {/* Main Content */}
        <div className="grid gap-8">
          {selectedUser ? (
            <div className="fade-in-up">
              <ChatInterface
                selectedUser={selectedUser}
                onBack={() => setSelectedUser(null)}
              />
            </div>
          ) : (
            <div className="fade-in-up">
              <div className="text-center mb-10">
                <div className="relative inline-block mb-6">
                  <MessageSquare className="w-20 h-20 mx-auto text-primary pulse-glow" />
                  <div className="absolute inset-0 bg-gradient-glow opacity-20 rounded-full blur-xl" />
                </div>
                <h2 className="text-3xl font-bold text-foreground/90 mb-4">
                  Conecte-se com o Mundo
                </h2>
                <p className="text-muted-foreground text-lg max-w-2xl mx-auto">
                  Encontre pessoas pelo email e inicie conversas incríveis em nossa plataforma cyber
                </p>
              </div>
              
              <UserSearch onSelectUser={setSelectedUser} />
            </div>
          )}
        </div>
      </div>
    </div>
  );
}