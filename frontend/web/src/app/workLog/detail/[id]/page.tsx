'use client';

import AllDetail from '@/components/AllDetail';
import TodayDetail from '@/components/TodayDetail';
import { useSession } from 'next-auth/react';
import { useParams, useSearchParams } from 'next/navigation';

export default function Detail() {
  const { data: session } = useSession();
  const { id } = useParams();

  const searchParams = useSearchParams();

  const userId = searchParams.get('nickName');
  const cropName = searchParams.get('cropName');
  const cropDate = searchParams.get('cropDate');

  return (
    <>
      <div className='flex flex-col items-center h-screen w-full pt-14'>
        <div className='w-3/5'>
          <p className='mt-14 font-nanumHeavy font-heavy text-2xl'>
            {userId}님의 작업일지 {'>'} {cropName}
          </p>
          <div className='mt-5'>
            <TodayDetail
              userId={session?.user.uid}
              cropId={id}
              cropDate={cropDate}
            />
          </div>

          <AllDetail
            userId={session?.user.uid}
            cropId={id}
            cropDate={cropDate}
          />
        </div>
      </div>
    </>
  );
}
