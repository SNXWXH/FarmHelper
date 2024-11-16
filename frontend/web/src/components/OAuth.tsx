import Image from 'next/image';
import Link from 'next/link';

export default function OAuth({ title }: { title: string }) {
  return (
    <>
      <p className='relative text-3xl'>
        간편 {title}
        <span className='absolute left-[-175px] top-1/2 transform -translate-y-1/2 w-[165px] border-t-[3px] border-black'></span>
        <span className='absolute right-[-175px] top-1/2 transform -translate-y-1/2 w-[165px] border-t-[3px] border-black'></span>
      </p>

      <Link
        href='/'
        className='flex justify-center items-center w-20 h-20 bg-[#F8F8F8] rounded-md shadow-2xl m-12'
      >
        <Image src='/google.png' alt='google' width={30} height={0} />
      </Link>
    </>
  );
}
