import Link from 'next/link';
import React, { useEffect, useState } from 'react';
import { useSession } from 'next-auth/react';

const WorkLog = ({ userId, cropId }: { userId: string; cropId: number }) => {
  const [workLogs, setWorkLogs] = useState<any[]>([]);
  const [today, setToday] = useState('');
  const [isLatestToday, setIsLatestToday] = useState(false);
  const [latestWorkLog, setLatestWorkLog] = useState<any | null>(null);

  const { data: session } = useSession();

  const fetchWorkLogs = async () => {
    try {
      const response = await fetch(
        `/api/getWorkLog?userId=${userId}&cropId=${cropId}`
      );
      const data = await response.json();

      setWorkLogs(data.workLogs);
      setToday(data.today);

      if (data.workLogs.length > 0) {
        const latestLog = data.workLogs[data.workLogs.length - 1];
        setLatestWorkLog(latestLog);
        setIsLatestToday(latestLog.workDate === data.today);
      }
    } catch (error) {
      console.error('Error fetching work logs:', error);
    }
  };

  useEffect(() => {
    fetchWorkLogs();
  }, [userId, cropId]);

  const handleComplete = async () => {
    if (!session?.user?.uid) {
      console.error('User is not authenticated');
      return;
    }

    try {
      const response = await fetch(
        `/api/deleteWorkDetail?workId=${latestWorkLog?.workId}&userId=${session.user.uid}&cropId=${cropId}`
      );

      if (!response.ok) {
        throw new Error(`Failed to delete work detail: ${response.status}`);
      }

      const data = await response.json();
      fetchWorkLogs();

      console.log('Deleted work detail:', data);
    } catch (error) {
      console.error('Error deleting work detail:', error);
    }
  };

  return (
    <>
      <div>
        {workLogs.length === 0 ? (
          <>
            <div className='w-full bg-[#F2FAE7] rounded-2xl p-7 my-6'>
              <div className='flex flex-col'>
                <div className='flex flex-col justify-center items-center h-full gap-5 p-8'>
                  <p className='text-2xl'>오늘의 할 일이 비어있습니다.</p>
                  <Link
                    href={`/workLog/detail/${cropId}/write?userId=${userId}&cropId=${cropId}`}
                    passHref
                  >
                    <button className='flex justify-center items-center w-28 h-12 bg-[#698A54] rounded-xl text-lg text-white'>
                      AI 추천
                    </button>
                  </Link>
                </div>
              </div>
            </div>
          </>
        ) : (
          <div>
            {latestWorkLog && (
              <div key={latestWorkLog.workId}>
                <div className='flex items-center justify-between'>
                  <div className='flex items-center'>
                    <p className='text-xl font-extrabold'>
                      {latestWorkLog.workDate}
                    </p>
                    <p className='ml-4 font-extrabold'>
                      날씨: {latestWorkLog.workWeather} 온도:{' '}
                      {latestWorkLog.workTemperature}
                    </p>
                  </div>
                  <div className='flex'>
                    <Link
                      href={`/workLog/detail/${cropId}/rewrite?userId=${userId}&cropId=${cropId}`}
                      passHref
                    >
                      <button className='flex items-center justify-center w-20 h-10 bg-[#698A54] rounded-xl text-white mr-3'>
                        수정
                      </button>
                    </Link>
                    <button
                      onClick={handleComplete}
                      className='flex items-center justify-center w-20 h-10 bg-[#698A54] rounded-xl text-white'
                    >
                      삭제
                    </button>
                  </div>
                </div>
                <div className='w-full bg-[#F2FAE7] rounded-2xl p-7 my-6'>
                  <div className='flex flex-col'>
                    {latestWorkLog.workContent
                      .split('q!gL9A')
                      .map((todo, idx) => (
                        <p className='my-1' key={idx}>
                          • {todo}
                        </p>
                      ))}
                  </div>
                </div>
              </div>
            )}
          </div>
        )}
      </div>
    </>
  );
};

export default WorkLog;