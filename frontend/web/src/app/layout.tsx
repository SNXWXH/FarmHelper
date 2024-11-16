import type { Metadata } from 'next';
import './globals.css';
import Header from '@/components/Header';

export const metadata: Metadata = {
  title: '농부의 하루',
  description: 'AI를 활용한 농사',
  icons: {
    icon: '/faviconLogo.png',
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang='en'>
      <body className='font-["NanumSquareNeo"] flex flex-col min-h-screen bg-[#F9FDF4] text-black'>
        <Header />
        {children}
      </body>
    </html>
  );
}
