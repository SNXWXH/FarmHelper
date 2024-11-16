import Image from 'next/image';

export default function CropCard() {
  return (
    <>
      <div className='flex flex-col justify-center items-center'>
        <div className='flex bg-[#F2FFE0] w-44 h-44 mt-8 justify-center items-center rounded-2xl shadow-xl'>
          <Image src='/temporaryImg.png' alt='img' width={140} height={0} />
        </div>
        <p className='font-extrabold text-xl mt-4'>토뭉이</p>
      </div>
    </>
  );
}
