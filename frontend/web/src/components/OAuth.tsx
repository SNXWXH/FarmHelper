'use client';

import { useSession, signIn, signOut } from 'next-auth/react';
import Image from 'next/image';

export default function OAuth() {
  const { data: session } = useSession();
  // console.log('🚀 session:', session);
  // console.log('🚀 uuid:', session?.id);

  return (
    <>
      {!session && (
        <p className='relative text-3xl'>
          간편 로그인
          <span className='absolute left-[-175px] top-1/2 transform -translate-y-1/2 w-[165px] border-t-[3px] border-black'></span>
          <span className='absolute right-[-175px] top-1/2 transform -translate-y-1/2 w-[165px] border-t-[3px] border-black'></span>
        </p>
      )}

      {session ? (
        <>
          <p className='text-2xl'>
            {session.user?.name}님 로그아웃 하시겠습니까?
          </p>
          <button
            className='flex justify-center items-center w-24 h-14 bg-[#698A54] rounded-md shadow-2xl m-12 text-white'
            onClick={() => signOut()}
          >
            로그아웃
          </button>
        </>
      ) : (
        <button
          className='flex justify-center items-center w-20 h-20 bg-[#F8F8F8] rounded-md shadow-2xl m-12'
          onClick={() => signIn('google', { callbackUrl: '/' })}
        >
          <Image src='/google.png' alt='google' width={30} height={0} />
        </button>
      )}
    </>
  );
}
