import Image from 'next/image';

export default function CropListCard({
  cropName,
  date,
}: {
  cropName: string;
  date: string;
}) {
  return (
    <>
      <div className='flex flex-col items-center bg-[#F4FEE6] w-60 h-72'>
        <div className='h-3/5 flex justify-center items-center mt-4'>
          <Image src='/temporaryImg.png' alt='img' width={140} height={0} />
        </div>
        <div className='flex flex-col items-center justify-center h-2/5 gap-3'>
          <p className='text-xl text-[#585858]'>{date}</p>
          <p className='text-2xl font-nanumHeavy font-heavy'>{cropName}</p>
        </div>
      </div>
    </>
  );
}
