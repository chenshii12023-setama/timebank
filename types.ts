export enum TaskStatus {
  PENDING = 'PENDING',
  ACCEPTED = 'ACCEPTED',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  DISPUTED = 'DISPUTED',
}

export enum UserRole {
  USER = 'USER',
  VOLUNTEER = 'VOLUNTEER',
  ADMIN = 'ADMIN',
}

export interface User {
  id: string;
  nickname: string;
  avatar: string;
  bio: string;
  skills: string[];
  rating: number; // Changed from creditScore to rating (0-5)
  timeCoins: number;
  role: UserRole;
  verified: boolean;
}

export interface Task {
  id: string;
  title: string;
  description: string;
  author: User;
  assignee?: User;
  coins: number;
  status: TaskStatus;
  tags: string[];
  location: string;
  createdAt: string;
  isCharity?: boolean;
  rating?: number; // Rating received for this task
  review?: string; // Review comment for this task
}

export interface Transaction {
  id: string;
  amount: number;
  type: 'EARN' | 'SPEND';
  description: string;
  date: string;
}

export interface Post {
  id: string;
  author: User;
  content: string;
  likes: number;
  comments: number;
  tags: string[];
  date: string;
}
