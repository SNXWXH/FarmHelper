import CropCard from '@/components/CropCard';
import MonthRank from '@/components/MonthRank';

export default function Home() {
  return (
    <>
      <div className='flex flex-col items-center h-screen pt-14 '>
        <div className='w-3/5'>
          <div className='font-nanumHeavy font-heavy mt-14'>
            <p className='text-2xl'>오늘의 날씨</p>
            <div className='mt-8 h-40 border'>날씨부분</div>
          </div>
          <div className='mt-14'>
            <p className='font-nanumHeavy font-heavy text-2xl'>
              오늘의 추천 작물
            </p>
            <div className='overflow-x-auto flex gap-6'>
              <CropCard />
              <CropCard />
              <CropCard />
              <CropCard />
              <CropCard />
              <CropCard />
              <CropCard />
              <CropCard />
            </div>
          </div>
          <div className='mt-14'>
            <p className='font-nanumHeavy font-heavy text-2xl'>
              이번 달 인기 작물
            </p>
            <MonthRank />
          </div>
          <div className='my-14 '>
            <p className='font-nanumHeavy font-heavy text-2xl'>
              오늘의 작업일지
            </p>
            <div className='flex justify-center items-center mt-8 h-40 text-xl font-bold'>
              로그인 후 오늘의 작업일지를 확인해보세요!
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
