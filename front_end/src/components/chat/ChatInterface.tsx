import { useState, useRef, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Send, ArrowLeft, User } from "lucide-react";
import { Client } from "@stomp/stompjs";

interface Message {
  id: string;
  content: string;
  senderId: string;
  timestamp: Date;
  isOwn: boolean;
}

interface User {
  id: string;
  name: string;
  email: string;
  avatar?: string;
}

interface ChatInterfaceProps {
  selectedUser: User;
  onBack: () => void;
}

export function ChatInterface({ selectedUser, onBack }: ChatInterfaceProps) {
  
  const [messages, setMessages] = useState<Message[]>([]);
  const [newMessage, setNewMessage] = useState("");
  const messagesEndRef = useRef<HTMLDivElement>(null);
  const stompClientRef = useRef<Client | null>(null);

  const currentUserId = JSON.parse(localStorage.getItem("user")).idPublic; // 👉 depois substitui pelo ID real do usuário logado
  const getIdPublicChat = localStorage.getItem("chatIdPublic");

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);


  // 🔌 Pega o histórico de mensagens
useEffect(() => {

  fetch("http://localhost:8080/chat/historical", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(getIdPublicChat),
  })
    .then(async (response) => {
      if (!response.ok) {
        if (response.status === 409) {
          throw new Error("Chat já existe");
        }

        console.log(response.json);
        throw new Error("Erro ao criar chat");
      }

      const data = await response.json();
      console.log(data);

      if (data.message) {
        data.message.forEach((msg) => {



          setMessages((prev) => [
            ...prev,
            {
              id: msg.id,
              content: msg.content,
              senderId: msg.sender.idPublic,
              timestamp: msg.timestamp,
              isOwn: msg.sender.idPublic === currentUserId,
              
            },
          ]);

          
          console.log("msg pegas: ", msg.content);
        });
      }
    })
    .catch((err) => {
      console.error("Erro:", err.message);
    });
}, []);






    // 🔌 Conectar STOMP ao montar o componente
    useEffect(() => {
      const stompClient = new Client({
        brokerURL: "ws://localhost:8080/ws-chat",
        reconnectDelay: 5000,
        debug: (str) => console.log(str),
      });

      stompClient.onConnect = () => {
        console.log("✅ Conectado ao WebSocket!");

        // 🔔 Receber mensagens privadas
        stompClient.subscribe("/queue/" + getIdPublicChat, (msg) => {
          const message = JSON.parse(msg.body);
          console.log("📩 Recebida:", message);

          console.log("Date da msg: ", message.date)

          setMessages((prev) => [
            ...prev,
            {
              ...message,
              id: message.id,
              timestamp: message.date,
              isOwn: message.senderId === currentUserId,
            },
          ]);
        });
      };

      stompClient.activate();
      stompClientRef.current = stompClient;

      return () => {
        stompClient.deactivate();
      };
    }, [currentUserId]);

    // 📤 Enviar mensagem
    const handleSendMessage = (e: React.FormEvent) => {
      e.preventDefault();

      if (!newMessage.trim() || !stompClientRef.current?.connected) return;

      var getIdPublicChat = localStorage.getItem("chatIdPublic");
      var getIdPublicUserselect = localStorage.getItem("searchUser");

      const message = {
        chatIdPublic: getIdPublicChat,
        senderId: currentUserId,
        receiverId: getIdPublicUserselect,
        content: newMessage,
        date: new Date(),
      };

      stompClientRef.current.publish({
        destination: "/app/send",
        body: JSON.stringify(message),
      });

    };

  const formatTime = (date: string | Date) => {

    let fixedDate = typeof date === "string" && !date.endsWith("Z")
      ? date + "Z"
      : date;

    const dateConvert = new Date(fixedDate);

    return dateConvert.toLocaleTimeString("pt-BR", {
      hour: "2-digit",
      minute: "2-digit",
      timeZone: "America/Sao_Paulo",
    });
  };

    return (
      <div className="glass-effect cyber-glow laser-border rounded-lg h-[700px] flex flex-col hover-lift relative overflow-hidden">
        {/* Header */}
        <div className="flex items-center p-6 border-b border-primary/20 backdrop-blur-sm relative z-10">
          <Button
            variant="ghost"
            size="sm"
            onClick={onBack}
            className="mr-4 hover-lift"
          >
            <ArrowLeft className="w-4 h-4" />
          </Button>

          <div className="flex items-center space-x-4">
            <div className="relative">
              <div className="w-12 h-12 rounded-full bg-gradient-laser flex items-center justify-center text-sm font-bold shadow-glow">
                {selectedUser.avatar || (
                  <User className="w-6 h-6 text-primary-foreground" />
                )}
              </div>
              <div className="absolute -bottom-1 -right-1 w-4 h-4 bg-green-500 rounded-full border-2 border-background pulse-glow" />
            </div>
            <div>
              <p className="font-semibold text-foreground text-lg">
                {selectedUser.name}
              </p>
              <div className="flex items-center space-x-2">
                <div className="w-2 h-2 bg-green-500 rounded-full pulse-glow" />
                <p className="text-sm text-muted-foreground">Online agora</p>
              </div>
            </div>
          </div>
        </div>

        {/* Messages */}
        <div className="flex-1 overflow-y-auto p-6 space-y-6 relative z-10">
          {messages.map((message, index) => (
            <div
              key={message.id}
              className={`flex ${message.isOwn ? "justify-end" : "justify-start"} animate-fade-in-up`}
              style={{ animationDelay: `${index * 0.1}s` }}
            >
              <div
                className={`max-w-xs lg:max-w-md px-6 py-3 rounded-2xl relative ${message.isOwn
                    ? "bg-gradient-laser text-primary-foreground shadow-glow"
                    : "glass-effect text-foreground border border-primary/20"
                  } hover-lift transition-all duration-300`}
              >
                <p className="text-sm leading-relaxed">{message.content}</p>
                <p
                  className={`text-xs mt-2 ${message.isOwn
                      ? "text-primary-foreground/70"
                      : "text-muted-foreground"
                    }`}
                >
                  {formatTime(message.timestamp)}
                </p>
              </div>
            </div>
          ))}
          <div ref={messagesEndRef} />
        </div>

        {/* Input */}
        <form
          onSubmit={handleSendMessage}
          className="p-6 border-t border-primary/20 backdrop-blur-sm relative z-10"
        >
          <div className="flex space-x-4">
            <Input
              value={newMessage}
              onChange={(e) => setNewMessage(e.target.value)}
              placeholder="Digite sua mensagem..."
              className="h-12 pr-4 glass-effect border-primary/30 focus:border-primary focus:ring-2 focus:ring-primary/20 cyber-glow transition-all duration-300"
            />
            <Button
              type="submit"
              variant="laser"
              size="icon"
              className="shrink-0 w-12 h-12 hover-lift"
            >
              <Send className="w-5 h-5" />
            </Button>
          </div>
        </form>
      </div>
    );
  }
