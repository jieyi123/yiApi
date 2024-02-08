export default [

  { path: '/index', name: '欢迎', icon: 'smile', component: './Index/index' },
  { path: '/interface_info/:id', name: '查看接口', icon: 'smile', component: './InterfaceInfo',hideInMenu: true },
  {
    path: '/user',
    layout: false,
    routes: [
      { name: '登录', path: '/user/login', component: './User/Login' },
      { name: '注册', path: '/user/register', component: './User/Register' },
      { name: '忘记密码', path: '/user/retrieve', component: './User/RetrievePassword' },
    ],
  },
  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    access: 'canAdmin',
    routes: [
      { path: '/admin', redirect: '/admin/user-manager' },
      { path: '/admin/user-manager', icon: 'contactsOutlined',name: '用户管理', component: './Admin/UserManager' },
      { path: '/admin/interface-info', icon: 'appstoreOutlined',name: '接口管理', component: './Admin/InterfaceInfo' },
      { path: '/admin/interface-analysis', icon: 'analysis',name: '接口分析', component: './Admin/InterfaceAnalysis' },
    ],
  },
  { name: '个人设置', icon: 'user', path: '/user-settings', component: './UserSettings' },
  { path: '/', redirect: '/index' },
  { path: '*', layout: false, component: './404' },
];

