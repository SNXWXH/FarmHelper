'use client';

import { useState } from 'react';
import Image from 'next/image';
import Link from 'next/link';
import { useSession } from 'next-auth/react';

export default function Header() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const { data: session } = useSession();

  const toggleMenu = () => {
    setIsMenuOpen((prev) => !prev);
  };

  return (
    <div className='fixed top-0 left-0 flex justify-between bg-[#2B590F] text-white h-14 p-3 w-full font-nanumHeavy font-heavy'>
      <Link href='/'>
        <Image src='/HeaderLogo.png' alt='HeaderLogo' width={110} height={0} />
      </Link>

      <div className='relative'>
        <Image
          src='/login.png'
          alt='Login'
          width={35}
          height={0}
          className='ml-10 cursor-pointer'
          onClick={toggleMenu}
        />
        {isMenuOpen && (
          <div className='absolute right-0 bg-white text-black p-4 rounded-lg shadow-lg w-32'>
            <Link href='/login' className='block px-4 py-2 hover:bg-gray-200'>
              {session ? '로그아웃' : '로그인'}
            </Link>

            <Link href='/workLog' className='block px-4 py-2 hover:bg-gray-200'>
              작업일지
            </Link>
          </div>
        )}
      </div>
    </div>
  );
}
