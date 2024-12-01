'use client';

import { useState, useEffect } from 'react';
import { useSession } from 'next-auth/react';
import CropList from '@/components/CropList';
import CropListCard from '@/components/CropListCard';
import Link from 'next/link';

export default function WorkLog() {
  const [nickName, setNickName] = useState('');
  const [isGridView, setIsGridView] = useState(true);
  const [cropList, setCropList] = useState<any[]>([]);
  const { data: session } = useSession();

  useEffect(() => {
    const fetchCropData = async () => {
      if (session?.user.uid) {
        try {
          const response = await fetch(
            `/api/readWorkList?userId=${session.user.uid}`
          );
          const data = await response.json();
          setCropList(data.workList);
          setNickName(data.nickname);
        } catch (error) {
          console.error('Error fetching crop data:', error);
        }
      }
    };

    fetchCropData();
  }, [session?.user.uid]);

  return (
    <>
      <div className='flex flex-col items-center h-screen w-full pt-14'>
        <div className='w-3/5'>
          <p className='mt-14 font-nanumHeavy font-heavy text-2xl'>
            {nickName}님의 작업일지
          </p>
          <div className='flex justify-end mt-6 mb-9'>
            <button
              className={`px-4 py-2 text-sm font-medium rounded-md w-8 h-8 bg-no-repeat bg-center bg-cover 
                ${
                  isGridView ? 'bg-[url("/grid.png")]' : 'bg-[url("/list.png")]'
                }`}
              onClick={() => setIsGridView((prev) => !prev)}
            ></button>
          </div>
          <div
            className={`${
              isGridView
                ? 'grid grid-cols-3 gap-16 mb-14'
                : 'flex flex-col gap-9 mb-14'
            }`}
          >
            {isGridView ? (
              cropList.length > 0 ? (
                cropList.map((crop, idx) => (
                  <Link href={`workLog/detail/${crop.cropId}`} key={idx}>
                    <CropListCard
                      key={idx}
                      cropName={crop.cropName}
                      date={crop.cropDate}
                      imageUrl={decodeURIComponent(crop.imageUrl)}
                    />
                  </Link>
                ))
              ) : (
                <p className='flex justify-center items-center h-screen'>
                  작업일지가 없습니다.
                </p>
              )
            ) : cropList.length > 0 ? (
              cropList.map((crop, idx) => (
                <Link href={`workLog/detail/${idx}`} key={idx}>
                  <CropList
                    key={idx}
                    cropName={crop.cropName}
                    date={crop.cropDate}
                  />
                </Link>
              ))
            ) : (
              <p className='flex justify-center items-center w-full h-screen'>
                작업일지가 없습니다.
              </p>
            )}
          </div>
        </div>
      </div>
    </>
  );
}
