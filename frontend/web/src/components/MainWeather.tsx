export default function MainWeather() {
  return (
    <>
      <div className='mt-8 h-40 w-full flex justify-center bg-[#F2FFE0] rounded-2xl'>
        <div className='flex justify-center items-center w-1/2 font-nanumHeavy font-heavy'>
          <p className='text-6xl'>6.5</p>
          <p className='text-3xl h-16'>°</p>
          <div className='ml-2'>
            <p className='text-2xl'>맑음 </p>
            <p>어제보다 2.5° 낮음</p>
          </div>
        </div>
        <div className='flex flex-col justify-center items-center w-1/2 gap-3 mr-8'>
          <div className='flex gap-8'>
            <div className='flex w-24 items-center'>
              <p className='mr-2 font-extrabold text-[#8B8E88]'>습도</p>
              <p className='font-nanumHeavy font-heavy text-sm'>45%</p>
            </div>
            <div className='flex w-24 items-center'>
              <p className='mr-2 font-extrabold text-[#8B8E88]'>습도</p>
              <p className='font-nanumHeavy font-heavy text-sm'>6.5°</p>
            </div>
            <div className='flex w-24 items-center'>
              <p className='mr-2 font-extrabold text-[#8B8E88]'>서풍</p>
              <p className='font-nanumHeavy font-heavy text-sm'>1.1m/s</p>
            </div>
          </div>
          <div className='flex gap-8'>
            <div className='flex w-24 items-center'>
              <p className='mr-2 font-extrabold text-[#8B8E88]'>미세</p>
              <p className='font-nanumHeavy font-heavy text-sm text-blue-400'>
                좋음
              </p>
            </div>
            <div className='flex w-24 items-center'>
              <p className='mr-2 font-extrabold text-[#8B8E88]'>초미세</p>
              <p className='font-nanumHeavy font-heavy text-sm text-blue-400'>
                좋음
              </p>
            </div>
            <div className='flex w-24 items-center'>
              <p className='mr-2 font-extrabold text-[#8B8E88]'>일출</p>
              <p className='font-nanumHeavy font-heavy text-sm'>07:03</p>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
