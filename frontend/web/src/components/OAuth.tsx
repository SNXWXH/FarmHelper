'use client';

import { useSession, signIn, signOut } from 'next-auth/react';
import Image from 'next/image';

export default function OAuth({ title }: { title: string }) {
  const { data: session } = useSession();

  return (
    <>
      <p className='relative text-3xl'>
        간편 {title}
        <span className='absolute left-[-175px] top-1/2 transform -translate-y-1/2 w-[165px] border-t-[3px] border-black'></span>
        <span className='absolute right-[-175px] top-1/2 transform -translate-y-1/2 w-[165px] border-t-[3px] border-black'></span>
      </p>

      {session ? (
        <button
          className='flex justify-center items-center w-20 h-20 bg-[#F8F8F8] rounded-md shadow-2xl m-12'
          onClick={() => signOut()}
        >
          <Image src='/logged_in.png' alt='logged in' width={30} height={0} />
        </button>
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
