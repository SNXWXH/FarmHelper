import Link from 'next/link';
import React, { useEffect, useState } from 'react';

export default function AllDetail({
  userId,
  cropId,
}: {
  userId: string;
  cropId: number;
}) {
  const [data, setData] = useState<string[][]>([]);
  const [isReversed, setIsReversed] = useState(true);

  useEffect(() => {
    const fetchWorkLogs = async () => {
      try {
        const response = await fetch(
          `/api/getWorkLog?userId=${userId}&cropId=${cropId}`
        );
        const data = await response.json();

        if (data.workLogs && Array.isArray(data.workLogs)) {
          const workLogs = data.workLogs.slice(0, -1);
          setData(workLogs);
        } else {
          setData([]);
        }
      } catch (error) {
        console.error('Error fetching work logs:', error);
        setData([]);
      }
    };

    fetchWorkLogs();
  }, [userId, cropId]);

  const handleReverse = () => {
    setIsReversed(!isReversed);
  };

  const displayedData = isReversed ? [...data].reverse() : data;

  return (
    <>
      {displayedData.length === 0 ? null : (
        <>
          <div className='w-full h-0.5 bg-black my-6'></div>
          <div className='flex flex-col'>
            <div className='flex justify-end'>
              <button
                className='w-28 h-9 bg-[#B3D99C] text-white rounded-xl mb-6'
                onClick={handleReverse}
              >
                {isReversed ? '내림차순' : '오름차순'}
              </button>
            </div>
            <div className='flex flex-col mb-8'>
              {displayedData.map((list, idx) => (
                <div key={idx}>
                  <div className='flex items-center'>
                    <p className='text-xl font-extrabold'>{list.workDate}</p>
                    <p className='ml-4 font-extrabold'>
                      날씨: {list.workWeather} 온도: {list.workTemperature}
                    </p>
                  </div>
                  <div className='w-full bg-[#F2FAE7] rounded-2xl p-7 my-6'>
                    <div className='flex flex-col'>
                      {list.workContent.split('q!gL9A').map((todo, idx) => (
                        <p className='my-1' key={idx}>
                          • {todo}
                        </p>
                      ))}
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </>
      )}
    </>
  );
}