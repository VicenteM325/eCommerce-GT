import { INavData } from '@coreui/angular';

export function getNavItems(role: string | undefined): INavData[] {
  const commonItems: INavData[] = [
    {
      name: 'Dashboard',
      url: '/dashboard',
      iconComponent: { name: 'cil-speedometer' },
      badge: { color: 'info', text: 'NEW' }
    },
    {
      title: true,
      name: 'Theme'
    },
    {
      name: 'Colors',
      url: '/theme/colors',
      iconComponent: { name: 'cil-drop' }
    },
    {
      name: 'Typography',
      url: '/theme/typography',
      iconComponent: { name: 'cil-pencil' }
    }
  ];

  const adminItems: INavData[] = [
    {
      title: true,
      name: 'Admin'
    },
    {
      name: 'Usuarios',
      url: '/admin/users',
      iconComponent: { name: 'cil-people' }
    },
    {
      name: 'Settings',
      url: '/settings',
      iconComponent: { name: 'cil-settings' }
    },
    {
      name: 'Reports',
      url: '/reports',
      iconComponent: { name: 'cil-chart-pie' },
      children: [
        { name: 'Sales', url: '/reports/sales', icon: 'nav-icon-bullet' },
        { name: 'Users', url: '/reports/users', icon: 'nav-icon-bullet' }
      ]
    }
  ];

  const commonUserItems: INavData[] = [
    {
      title: true,
      name: 'User'
    },
    {
      name: 'My Posts',
      url: '/my-posts',
      iconComponent: { name: 'cil-note' }
    },
    {
      name: 'Messages',
      url: '/messages',
      iconComponent: { name: 'cil-envelope-open' },
      badge: { color: 'success', text: '5' }
    },
    {
      name: 'Extras',
      url: '/extras',
      iconComponent: { name: 'cil-star' },
      children: [
        { name: 'Profile', url: '/profile', icon: 'nav-icon-bullet' },
        { name: 'Settings', url: '/settings', icon: 'nav-icon-bullet' }
      ]
    }
  ];

  switch (role) {
    case 'ROLE_ADMIN':
      return [...commonItems, ...adminItems];
    case 'ROLE_COMMON':
      return [...commonItems, ...commonUserItems];
    default:
      return commonItems; 
  }
}
