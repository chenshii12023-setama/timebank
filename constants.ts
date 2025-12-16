import { Task, TaskStatus, User, UserRole, Transaction, Post } from './types';

export const CURRENT_USER: User = {
  id: 'u1',
  nickname: '陈阿杰',
  avatar: 'https://picsum.photos/100/100?random=1',
  bio: '前端开发 & 周末志愿者。乐于帮忙解决技术问题！',
  skills: ['电脑维修', '编程', '遛狗'],
  rating: 4.8,
  timeCoins: 12.5,
  role: UserRole.VOLUNTEER,
  verified: true,
};

export const MOCK_TASKS: Task[] = [
  {
    id: 't1',
    title: '帮忙搬运沙发',
    description: '需要两位力气大的人帮忙把沙发搬到二楼。没有电梯。',
    author: {
      id: 'u2',
      nickname: '李莎莎',
      avatar: 'https://picsum.photos/100/100?random=2',
      bio: '美术老师。',
      skills: ['绘画', '教学'],
      rating: 4.9,
      timeCoins: 5,
      role: UserRole.USER,
      verified: true
    },
    coins: 2,
    status: TaskStatus.PENDING,
    tags: ['体力', '搬家'],
    location: '市中心公寓',
    createdAt: '2小时前'
  },
  {
    id: 't2',
    title: '海滩清洁活动',
    description: '官方社区活动。加入我们一起清洁北海滩。提供手套和工具。',
    author: {
      id: 'admin1',
      nickname: '社区中心',
      avatar: 'https://picsum.photos/100/100?random=99',
      bio: '官方账号',
      skills: [],
      rating: 5.0,
      timeCoins: 9999,
      role: UserRole.ADMIN,
      verified: true
    },
    coins: 4,
    status: TaskStatus.PENDING,
    tags: ['环保', '团建'],
    location: '北海滩',
    createdAt: '5小时前',
    isCharity: true
  },
  {
    id: 't3',
    title: '初二数学辅导',
    description: '寻找一位能辅导我儿子代数基础的人。',
    author: {
      id: 'u3',
      nickname: '王强',
      avatar: 'https://picsum.photos/100/100?random=3',
      bio: '两个孩子的父亲。',
      skills: ['烹饪'],
      rating: 4.5,
      timeCoins: 10,
      role: UserRole.USER,
      verified: true
    },
    coins: 3,
    status: TaskStatus.IN_PROGRESS,
    tags: ['教育', '数学'],
    location: '线上 / Zoom',
    createdAt: '1天前'
  },
  {
    id: 't4',
    title: '帮助设计简单的Logo',
    description: '为我的小面包店设计一个简单的Logo。',
    author: {
        id: 'u2',
        nickname: '李莎莎',
        avatar: 'https://picsum.photos/100/100?random=2',
        bio: '美术老师。',
        skills: ['绘画', '教学'],
        rating: 4.9,
        timeCoins: 5,
        role: UserRole.USER,
        verified: true
    },
    assignee: CURRENT_USER, // User participated
    coins: 5,
    status: TaskStatus.COMPLETED,
    tags: ['设计', '绘画'],
    location: '线上',
    createdAt: '3天前',
    rating: 5.0,
    review: '设计非常有创意，响应速度很快，非常感谢！'
  }
];

export const MOCK_TRANSACTIONS: Transaction[] = [
  { id: 'tx1', amount: 2.0, type: 'EARN', description: '帮邻居修WiFi', date: '2023-10-25' },
  { id: 'tx2', amount: 1.5, type: 'SPEND', description: '代买杂货', date: '2023-10-20' },
  { id: 'tx3', amount: 4.0, type: 'EARN', description: '公园清洁志愿者', date: '2023-10-15' },
];

export const MOCK_POSTS: Post[] = [
  {
    id: 'p1',
    author: CURRENT_USER,
    content: '刚结束了一场很棒的Python基础教学！时间银行太棒了。',
    likes: 24,
    comments: 5,
    tags: ['技能交换', '科技'],
    date: '10分钟前'
  },
  {
    id: 'p2',
    author: {
        id: 'u4',
        nickname: '林达',
        avatar: 'https://picsum.photos/100/100?random=4',
        bio: '园艺师',
        skills: ['园艺'],
        rating: 4.7,
        timeCoins: 8,
        role: UserRole.USER,
        verified: true
    },
    content: '有人知道怎么修漏水的水龙头吗？愿意用2小时园艺服务交换！',
    likes: 12,
    comments: 8,
    tags: ['求助', '管道维修'],
    date: '2小时前'
  }
];
