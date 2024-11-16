import OAuth from '@/components/OAuth';
import Link from 'next/link';

export default function Login() {
  return (
    <>
      <div className='flex flex-col justify-center items-center h-screen font-nanumHeavy font-heavy'>
        <OAuth title='로그인' />

        <Link href='/signup'>
          <p className='font-extrabold text-xl text-[#B9B9B9]'>간편 회원가입</p>
        </Link>
      </div>
    </>
  );
}
