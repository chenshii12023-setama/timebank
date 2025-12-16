import React, { useState } from 'react';
import { Settings, CreditCard, ShieldAlert, Award, ChevronRight, LogOut, Star } from 'lucide-react';
import { CURRENT_USER, MOCK_TRANSACTIONS } from '../constants';
import { useNavigate } from 'react-router-dom';

const Profile: React.FC = () => {
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState<'overview' | 'wallet'>('overview');

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Top Section */}
      <div className="bg-white pb-6 rounded-b-3xl shadow-sm">
        <div className="px-6 py-4 flex justify-between items-center">
          <h1 className="text-xl font-bold text-slate-800">个人中心</h1>
          <button className="text-gray-400 hover:text-gray-600"><Settings size={20} /></button>
        </div>

        <div className="px-6 flex items-center space-x-4 mb-6">
          <div className="relative">
            <img src={CURRENT_USER.avatar} alt="Profile" className="w-20 h-20 rounded-full border-4 border-white shadow-md" />
            <div className="absolute -bottom-1 -right-1 bg-yellow-400 text-white p-1 rounded-full border-2 border-white">
              <Star size={12} fill="currentColor" />
            </div>
          </div>
          <div>
            <h2 className="text-xl font-bold text-gray-900">{CURRENT_USER.nickname}</h2>
            <p className="text-sm text-gray-500 mb-1">{CURRENT_USER.role === 'VOLUNTEER' ? '🌟 社区志愿者' : '社区成员'}</p>
            <div className="flex items-center space-x-2">
              <div className="flex items-center bg-orange-50 text-orange-600 px-2 py-0.5 rounded-full text-xs font-bold">
                <Star size={10} className="fill-current mr-1" />
                {CURRENT_USER.rating}
              </div>
              {CURRENT_USER.skills.slice(0, 2).map(skill => (
                <span key={skill} className="text-xs bg-gray-100 text-gray-600 px-2 py-0.5 rounded-full">
                  {skill}
                </span>
              ))}
            </div>
          </div>
        </div>

        {/* Stats Row */}
        <div className="flex justify-around px-6">
          <div className="text-center" onClick={() => setActiveTab('wallet')}>
            <p className="text-xs text-gray-400 font-medium uppercase tracking-wider">余额</p>
            <p className="text-2xl font-bold text-teal-600">{CURRENT_USER.timeCoins} <span className="text-xs text-gray-400 font-normal">币</span></p>
          </div>
          <div className="w-px bg-gray-100 h-10"></div>
          <div className="text-center">
            <p className="text-xs text-gray-400 font-medium uppercase tracking-wider">服务时长</p>
            <p className="text-2xl font-bold text-slate-700">42</p>
          </div>
          <div className="w-px bg-gray-100 h-10"></div>
          <div className="text-center">
            <p className="text-xs text-gray-400 font-medium uppercase tracking-wider">星级</p>
            <div className="flex items-center justify-center text-2xl font-bold text-slate-700">
               {CURRENT_USER.rating} <Star size={16} className="text-yellow-400 fill-current ml-1" />
            </div>
          </div>
        </div>
      </div>

      <div className="p-4">
        {/* Toggle Tabs */}
        <div className="flex bg-gray-200 p-1 rounded-xl mb-6">
          <button 
            className={`flex-1 py-2 text-sm font-medium rounded-lg transition-all ${activeTab === 'overview' ? 'bg-white shadow-sm text-gray-800' : 'text-gray-500'}`}
            onClick={() => setActiveTab('overview')}
          >
            功能菜单
          </button>
          <button 
            className={`flex-1 py-2 text-sm font-medium rounded-lg transition-all ${activeTab === 'wallet' ? 'bg-white shadow-sm text-gray-800' : 'text-gray-500'}`}
            onClick={() => setActiveTab('wallet')}
          >
            钱包流水
          </button>
        </div>

        {activeTab === 'overview' ? (
          <div className="space-y-3">
            <div className="bg-white rounded-xl shadow-sm overflow-hidden">
               <button 
                 onClick={() => navigate('/my-tasks')}
                 className="w-full flex items-center justify-between px-4 py-4 hover:bg-gray-50 transition-colors border-b border-gray-50"
               >
                <div className="flex items-center space-x-3">
                  <div className="bg-blue-100 p-2 rounded-lg text-blue-600"><CreditCard size={18} /></div>
                  <span className="text-sm font-medium text-gray-700">我的任务 (含历史评分)</span>
                </div>
                <ChevronRight size={16} className="text-gray-400" />
              </button>
               <button className="w-full flex items-center justify-between px-4 py-4 hover:bg-gray-50 transition-colors border-b border-gray-50">
                <div className="flex items-center space-x-3">
                  <div className="bg-orange-100 p-2 rounded-lg text-orange-600"><Award size={18} /></div>
                  <span className="text-sm font-medium text-gray-700">志愿者勋章</span>
                </div>
                <ChevronRight size={16} className="text-gray-400" />
              </button>
               <button 
                 onClick={() => navigate('/arbitration')}
                 className="w-full flex items-center justify-between px-4 py-4 hover:bg-gray-50 transition-colors"
                >
                <div className="flex items-center space-x-3">
                  <div className="bg-red-100 p-2 rounded-lg text-red-600"><ShieldAlert size={18} /></div>
                  <span className="text-sm font-medium text-gray-700">争议与仲裁</span>
                </div>
                <ChevronRight size={16} className="text-gray-400" />
              </button>
            </div>

            <button className="w-full bg-white rounded-xl shadow-sm p-4 text-red-500 font-medium flex items-center justify-center space-x-2">
              <LogOut size={18} />
              <span>退出登录</span>
            </button>
          </div>
        ) : (
          <div className="space-y-3">
            <h3 className="text-xs font-semibold text-gray-500 uppercase tracking-wide mb-2 pl-2">近期交易</h3>
            {MOCK_TRANSACTIONS.map((tx) => (
              <div key={tx.id} className="bg-white rounded-xl p-4 shadow-sm flex justify-between items-center">
                <div>
                  <p className="text-sm font-medium text-gray-800">{tx.description}</p>
                  <p className="text-xs text-gray-400">{tx.date}</p>
                </div>
                <div className={`text-sm font-bold ${tx.type === 'EARN' ? 'text-teal-600' : 'text-gray-800'}`}>
                  {tx.type === 'EARN' ? '+' : '-'}{tx.amount} 币
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default Profile;