'use client';

import { GoogleAuthProvider, signInWithPopup } from 'firebase/auth';
import Image from 'next/image';
import { auth } from '../../firebase-config';
import { signIn, signOut, useSession } from 'next-auth/react';

export default function OAuth() {
  const handleLogin = async () => {
    try {
      const provider = new GoogleAuthProvider();
      const result = await signInWithPopup(auth, provider);

      const user = result.user;

      if (!user.uid || !user.email) {
        throw new Error('Firebase 인증 결과에 UID 또는 Email이 없습니다.');
      }

      const response = await signIn('credentials', {
        uid: user.uid,
        _email: user.email,
        get email() {
          return this._email;
        },
        set email(value) {
          this._email = value;
        },
        name: user.displayName || 'Unknown User',
        redirect: false,
      });

      if (response?.ok) {
        console.log('NextAuth 세션 생성 성공:', response);
      } else {
        console.error('NextAuth 세션 생성 실패:', response?.error);
      }
    } catch (error) {
      console.error('Firebase 로그인 실패:', error);
    }
  };

  const { data: session } = useSession();

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
          onClick={handleLogin}
        >
          <Image src='/google.png' alt='google' width={30} height={0} />
        </button>
      )}
    </>
  );
}
