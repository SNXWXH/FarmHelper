import type { Metadata } from 'next';
import './globals.css';

export const metadata: Metadata = {
  title: '농부의 하루',
  description: 'AI를 활용한 농사',
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang='en'>
      <body className='font-["NanumSquareNeo"] flex flex-col min-h-screen bg-[#F9FDF4] text-black'>
        {children}
      </body>
    </html>
  );
}
