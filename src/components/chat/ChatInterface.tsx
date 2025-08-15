import { useState, useRef, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Send, ArrowLeft, User } from "lucide-react";

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
  const [messages, setMessages] = useState<Message[]>([
    {
      id: "1",
      content: "Olá! Como você está?",
      senderId: selectedUser.id,
      timestamp: new Date(Date.now() - 60000),
      isOwn: false
    },
    {
      id: "2",
      content: "Oi! Estou bem, obrigado por perguntar! E você?",
      senderId: "current-user",
      timestamp: new Date(Date.now() - 30000),
      isOwn: true
    }
  ]);
  const [newMessage, setNewMessage] = useState("");
  const messagesEndRef = useRef<HTMLDivElement>(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const handleSendMessage = (e: React.FormEvent) => {
    e.preventDefault();
    if (!newMessage.trim()) return;

    const message: Message = {
      id: Date.now().toString(),
      content: newMessage,
      senderId: "current-user",
      timestamp: new Date(),
      isOwn: true
    };

    setMessages(prev => [...prev, message]);
    setNewMessage("");
  };

  const formatTime = (date: Date) => {
    return date.toLocaleTimeString('pt-BR', { 
      hour: '2-digit', 
      minute: '2-digit' 
    });
  };

  return (
    <div className="glass-effect cyber-glow laser-border rounded-lg h-[700px] flex flex-col hover-lift relative overflow-hidden">
      {/* Background Pattern */}
      <div className="absolute inset-0 mesh-gradient opacity-5 pointer-events-none" />
      
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
              {selectedUser.avatar || <User className="w-6 h-6 text-primary-foreground" />}
            </div>
            <div className="absolute -bottom-1 -right-1 w-4 h-4 bg-green-500 rounded-full border-2 border-background pulse-glow" />
          </div>
          <div>
            <p className="font-semibold text-foreground text-lg">{selectedUser.name}</p>
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
            className={`flex ${message.isOwn ? 'justify-end' : 'justify-start'} animate-fade-in-up`}
            style={{ animationDelay: `${index * 0.1}s` }}
          >
            <div
              className={`max-w-xs lg:max-w-md px-6 py-3 rounded-2xl relative ${
                message.isOwn
                  ? 'bg-gradient-laser text-primary-foreground shadow-glow'
                  : 'glass-effect text-foreground border border-primary/20'
              } hover-lift transition-all duration-300`}
            >
              <p className="text-sm leading-relaxed">{message.content}</p>
              <p className={`text-xs mt-2 ${
                message.isOwn ? 'text-primary-foreground/70' : 'text-muted-foreground'
              }`}>
                {formatTime(message.timestamp)}
              </p>
              
              {/* Message indicator */}
              <div className={`absolute top-3 ${
                message.isOwn ? '-left-2' : '-right-2'
              } w-4 h-4 rounded-full ${
                message.isOwn ? 'bg-gradient-laser' : 'bg-secondary/50 border border-primary/20'
              } shadow-sm`} />
            </div>
          </div>
        ))}
        <div ref={messagesEndRef} />
      </div>

      {/* Message Input */}
      <form onSubmit={handleSendMessage} className="p-6 border-t border-primary/20 backdrop-blur-sm relative z-10">
        <div className="flex space-x-4">
          <div className="flex-1 relative group">
            <Input
              value={newMessage}
              onChange={(e) => setNewMessage(e.target.value)}
              placeholder="Digite sua mensagem..."
              className="h-12 pr-4 glass-effect border-primary/30 focus:border-primary focus:ring-2 focus:ring-primary/20 cyber-glow transition-all duration-300"
            />
          </div>
          <Button 
            type="submit" 
            variant="laser" 
            size="icon" 
            className="shrink-0 w-12 h-12 hover-lift"
          >
            <Send className="w-5 h-5" />
          </Button>
        </div>
        
        <div className="flex items-center justify-between mt-3 text-xs text-muted-foreground">
          <span>Pressione Enter para enviar</span>
          <div className="flex items-center space-x-1">
            <div className="w-2 h-2 bg-green-500 rounded-full pulse-glow" />
            <span>Online</span>
          </div>
        </div>
      </form>
    </div>
  );
}