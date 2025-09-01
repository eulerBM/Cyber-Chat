import React, { useRef, useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Search, MessageCircle, User as UserIcon } from "lucide-react";
import { set } from "date-fns";

interface User {
  idPublic: string;
  name: string;
  email: string;
  avatar?: string;
  [key: string]: any;
}

interface UserSearchProps {
  onSelectUser: (user: User) => void;
  
}

function createChat(user: User) {
  
  // Pega o usuário logado do localStorage (ajuste conforme sua implementação)
  const loggedUser = JSON.parse(localStorage.getItem("user") || "{}");
  const searchUser = localStorage.getItem("searchUser") || "{}";

  // Monta o DTO que o backend espera
  const data = {

    idPublicUserSend: loggedUser.idPublic,   // usuário logado
    idPublicUserReceived: searchUser      // usuário selecionado
   
  };

  fetch("http://localhost:8080/chat/create", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(data)

  })
    .then(async (response) => {

      if (!response.ok) {

            if (response.status === 409){

              throw new Error("Chat ja existe")
              
            }
            else if(response.status === 404){

              throw new Error("Usuario não encontrado")

            }

            console.log(response.json)
            throw new Error("Erro ao criar chat");
          }

      const data = await response.json()

      

      if(data.idPublicChat){

        localStorage.setItem("chatIdPublic", data.idPublicChat)

      }

      console.log(data)

    
      })
      .then((chatId) => {
      console.info("Usuário criado no banco com sucesso");
      console.log("ID do chat:", chatId);
})
}

export function UserSearch({ onSelectUser }: UserSearchProps) {
  const [searchEmail, setSearchEmail] = useState("");
  const [searchResults, setSearchResults] = useState<User[]>([]);
  const [isSearching, setIsSearching] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const abortRef = useRef<AbortController | null>(null);

  // sanitize: remove sensitive fields (password) and normalize id => string
  const sanitizeRawUser = (raw: any): User => {
    return {
      idPublic: raw?.idPublic != null ? String(raw.idPublic) : "",
      name: raw?.name ?? raw?.username ?? "Sem nome",
      email: raw?.email ?? "",
      avatar: raw?.avatar ?? undefined,
      // keep other non-sensitive fields if needed (but exclude password)
    };
  };

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    const email = searchEmail.trim();
    if (!email) return;

    if (abortRef.current) abortRef.current.abort();
    const controller = new AbortController();
    abortRef.current = controller;

    setIsSearching(true);
    setSearchResults([]);

    try {
      localStorage.removeItem("searchUser")
      const url = "http://localhost:8080/search/email"; // POST endpoint
      const res = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        signal: controller.signal,
        body: JSON.stringify({ email }),
      });

      // Try to parse JSON (graceful if not JSON)
      let data: any = null;
      try {

        data = await res.json();

        localStorage.setItem("searchUser", data.idPublic)
  
      } catch {
        data = null;
      }

      if (!res.ok) {
        const msg = data?.message || data?.error || res.statusText || `Erro ${res.status}`;
        throw new Error(msg);
      }


      // Normalize possible response shapes:
      // 1) single entity { id, name, email, password } => wrap into array
      // 2) array => use directly
      // 3) { data: [...] } or { users: [...] } => use the inner array
      let raws: any[] = [];

      if (!data) {
        raws = [];
      } else if (Array.isArray(data)) {
        raws = data;
      } else if (Array.isArray(data?.data)) {
        raws = data.data;
      } else if (Array.isArray(data?.users)) {
        raws = data.users;
      } else if (data?.id !== undefined || data?.name !== undefined || data?.email !== undefined) {
        // single user object
        raws = [data];
      } else {
        // unknown shape: try to find first array in response
        const arr = Object.values(data).find((v) => Array.isArray(v));
        raws = Array.isArray(arr) ? arr : [];
      }

      const results: User[] = raws.map(sanitizeRawUser);

      setSearchResults(results);
      if (!results.length) setError("Nenhum usuário encontrado.");
    } catch (err: any) {
      if (err?.name === "AbortError") return;
      setError(err?.message || "Erro ao buscar usuários.");
    } finally {
      setIsSearching(false);
      abortRef.current = null;
    }
  };

  return (
    <div className="space-y-8">
      <div className="glass-effect cyber-glow laser-border p-8 rounded-lg hover-lift fade-in-up relative overflow-hidden">
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

      {error && (
        <div className="text-center text-sm text-red-600">
          {error}
        </div>
      )}

      {searchResults.length > 0 && (
        <div className="glass-effect cyber-glow laser-border p-8 rounded-lg hover-lift slide-in-right">
          <h3 className="text-xl font-semibold mb-6 text-center bg-gradient-laser bg-clip-text text-transparent">
            Usuários Encontrados
          </h3>
          <div className="space-y-4">
            {searchResults.map((user, index) => (
              <div
                key={user.id || index}
                className="group p-4 rounded-lg glass-effect border border-primary/20 hover:border-primary/40 hover-lift transition-all duration-300 slide-in-left"
                style={{ animationDelay: `${index * 0.06}s` }}
              >
                <div className="flex items-center justify-between">
                  <div className="flex items-center space-x-4">
                    <div className="relative">
                      <div className="w-12 h-12 rounded-full bg-gradient-laser flex items-center justify-center text-sm font-bold shadow-glow">
                        {user.avatar || <UserIcon className="w-6 h-6 text-primary-foreground" />}
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
                    onClick={() => { 
                      createChat(user);
                      onSelectUser(user)
                                    

                    }}
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
