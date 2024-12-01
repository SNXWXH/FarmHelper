'use client';

import AllDetail from '@/components/AllDetail';
import DeatilList from '@/components/DeatilList';
import TodayDetail from '@/components/TodayDetail';
import { useSession } from 'next-auth/react';
import { useParams } from 'next/navigation';

export default function Detail() {
  const { data: session } = useSession();
  const { id } = useParams();

  return (
    <>
      <div className='flex flex-col items-center h-screen w-full pt-14'>
        <div className='w-3/5'>
          <p className='mt-14 font-nanumHeavy font-heavy text-2xl'>
            이설아님의 작업일지 {'>'} 감자
          </p>
          <div className='mt-5'>
            <TodayDetail userId={session?.user.uid} cropId={id} />
          </div>
          <div className='w-full h-0.5 bg-black my-6'> </div>
          <div className='flex flex-col'>
            <div className='flex justify-end'>
              <button className='w-28 h-9 bg-[#B3D99C] text-white rounded-xl mb-6'>
                내림차순
              </button>
            </div>
            <div className='flex flex-col mb-8'>
              <AllDetail />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
