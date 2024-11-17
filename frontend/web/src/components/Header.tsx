import Image from 'next/image';
import Link from 'next/link';

export default function Header() {
  return (
    <>
      <div className='fixed top-0 left-0 flex justify-between bg-[#2B590F] text-white h-14 p-3 w-full font-nanumHeavy font-heavy'>
        <Link href='/'>
          <Image
            src='/HeaderLogo.png'
            alt='HeaderLogo'
            width={110}
            height={0}
          />
        </Link>
        <div className='flex w-3/5 justify-between text-lg font-extrabold'>
          <Link
            href='/weather'
            className='w-24 flex justify-center hover:text-[#669248]'
          >
            날씨
          </Link>
          <Link
            href='/workLog'
            className='w-24 flex justify-center hover:text-[#669248]'
          >
            작업일지
          </Link>
          <Link
            href='/mypage'
            className='w-24 flex justify-center hover:text-[#669248] '
          >
            마이페이지
          </Link>
        </div>
        <Link href='/login'>
          <Image
            src='/login.png'
            alt='Login'
            width={35}
            height={0}
            className='ml-10'
          />
        </Link>
      </div>
    </>
  );
}
