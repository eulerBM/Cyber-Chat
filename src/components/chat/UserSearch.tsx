import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Search, MessageCircle, User } from "lucide-react";

interface User {
  id: string;
  name: string;
  email: string;
  avatar?: string;
}

interface UserSearchProps {
  onSelectUser: (user: User) => void;
}

export function UserSearch({ onSelectUser }: UserSearchProps) {
  const [searchEmail, setSearchEmail] = useState("");
  const [searchResults, setSearchResults] = useState<User[]>([]);
  const [isSearching, setIsSearching] = useState(false);

  // Mock search function - replace with real API call
  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!searchEmail.trim()) return;

    setIsSearching(true);
    
    // Simulate API call
    setTimeout(() => {
      const mockResults: User[] = [
        {
          id: "1",
          name: "Ana Silva",
          email: searchEmail,
          avatar: "AS"
        },
        {
          id: "2", 
          name: "Carlos Santos",
          email: "carlos@email.com",
          avatar: "CS"
        }
      ].filter(user => user.email.includes(searchEmail.toLowerCase()));
      
      setSearchResults(mockResults);
      setIsSearching(false);
    }, 1000);
  };

  return (
    <div className="space-y-8">
      <div className="glass-effect cyber-glow laser-border p-8 rounded-lg hover-lift fade-in-up relative overflow-hidden">
        {/* Background Pattern */}
        <div className="absolute inset-0 mesh-gradient opacity-5 pointer-events-none" />
        
        <div className="relative z-10">
          <div className="text-center mb-6">
            <div className="w-12 h-12 mx-auto rounded-full bg-gradient-laser flex items-center justify-center pulse-glow mb-4">
              <Search className="w-6 h-6 text-primary-foreground" />
            </div>
            <h2 className="text-2xl font-bold bg-gradient-laser bg-clip-text text-transparent mb-2">
              Buscar Usuários
            </h2>
            <p className="text-muted-foreground">Digite o email para encontrar outros usuários</p>
          </div>
          
          <form onSubmit={handleSearch} className="space-y-6">
            <div className="relative group">
              <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-primary w-5 h-5 transition-colors group-focus-within:text-primary-glow" />
              <Input
                type="email"
                placeholder="Digite o email do usuário..."
                value={searchEmail}
                onChange={(e) => setSearchEmail(e.target.value)}
                className="pl-12 h-14 text-lg glass-effect border-primary/30 focus:border-primary focus:ring-2 focus:ring-primary/20 cyber-glow transition-all duration-300"
              />
            </div>
            
            <Button 
              type="submit" 
              variant="laser" 
              className="w-full h-12 text-lg font-medium hover-lift"
              disabled={isSearching}
            >
              {isSearching ? (
                <div className="flex items-center space-x-2">
                  <div className="w-5 h-5 border-2 border-primary-foreground border-t-transparent rounded-full animate-spin" />
                  <span>Buscando...</span>
                </div>
              ) : (
                "Buscar Usuários"
              )}
            </Button>
          </form>
        </div>
      </div>

      {searchResults.length > 0 && (
        <div className="glass-effect cyber-glow laser-border p-8 rounded-lg hover-lift slide-in-right">
          <h3 className="text-xl font-semibold mb-6 text-center bg-gradient-laser bg-clip-text text-transparent">
            Usuários Encontrados
          </h3>
          <div className="space-y-4">
            {searchResults.map((user, index) => (
              <div
                key={user.id}
                className="group p-4 rounded-lg glass-effect border border-primary/20 hover:border-primary/40 hover-lift transition-all duration-300 slide-in-left"
                style={{ animationDelay: `${index * 0.1}s` }}
              >
                <div className="flex items-center justify-between">
                  <div className="flex items-center space-x-4">
                    <div className="relative">
                      <div className="w-12 h-12 rounded-full bg-gradient-laser flex items-center justify-center text-sm font-bold shadow-glow">
                        {user.avatar || <User className="w-6 h-6 text-primary-foreground" />}
                      </div>
                      <div className="absolute -bottom-1 -right-1 w-4 h-4 bg-green-500 rounded-full border-2 border-background pulse-glow" />
                    </div>
                    <div>
                      <p className="font-semibold text-foreground text-lg">{user.name}</p>
                      <p className="text-muted-foreground">{user.email}</p>
                    </div>
                  </div>
                  
                  <Button
                    variant="cyber"
                    size="sm"
                    onClick={() => onSelectUser(user)}
                    className="group-hover:scale-105 transition-transform duration-300"
                  >
                    <MessageCircle className="w-4 h-4 mr-2" />
                    Conversar
                  </Button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}