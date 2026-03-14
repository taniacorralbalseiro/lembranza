import React from 'react';
import WelcomeHeader from './WelcomeHeader';
import ResumenTarjetas from './ResumenTarjetas';
import QuickActions from './QuickActions';
import TodayActivity from './TodayActivity.jsx';

const EmpleadoHome = () => {
  return (
    <div>
      <WelcomeHeader />
      <ResumenTarjetas />
      <QuickActions />
      <TodayActivity />
    </div>
  );
};

export default EmpleadoHome;
