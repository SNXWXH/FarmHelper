'use client';

import { useState, useEffect } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import TodoDetail from '@/components/TodoDetail';

const DetailWrite = () => {
  const [details, setDetails] = useState<string[]>([]);
  const [inputValue, setInputValue] = useState<string>('');
  const [nickName, setNickName] = useState<string>('');
  const [cropName, setCropName] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(true);

  const searchParams = useSearchParams();
  const router = useRouter();

  const userId = searchParams.get('userId');
  const cropId = searchParams.get('cropId');

  useEffect(() => {
    const fetchAIData = async () => {
      try {
        const response = await fetch(
          `${process.env.NEXT_PUBLIC_BASE_URL}/api/getAIWorkLog?userId=${userId}&cropId=${cropId}`
        );
        const data = await response.json();
        setCropName(data.cropName);
        setNickName(data.nickname);
        setDetails(data.recommendations);
      } catch (error) {
        console.error('Error fetching AI data:', error);
      } finally {
        setLoading(false);
      }
    };

    if (userId && cropId) {
      fetchAIData();
    }
  }, [userId, cropId]);

  const addDetail = () => {
    if (inputValue.trim()) {
      setDetails((prev) => [...prev, inputValue.trim()]);
      setInputValue('');
    }
  };

  const removeDetail = (index: number) => {
    setDetails((prev) => prev.filter((_, i) => i !== index));
  };

  const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') addDetail();
  };

  const handleComplete = async () => {
    if (details.length > 0) {
      const workContent = details.join('q!gL9A');

      try {
        const response = await fetch(
          `${process.env.NEXT_PUBLIC_BASE_URL}/api/createWorkDetail`,
          {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId, cropId, workContent }),
          }
        );

        router.back();

        if (!response.ok) {
          throw new Error(`Failed to create work detail: ${response.status}`);
        }
      } catch (error) {
        console.error('Error sending work detail:', error);
      }
    }
  };

  return (
    <div className='flex flex-col items-center h-screen w-full pt-14'>
      <div className='w-3/5'>
        <p className='mt-14 font-nanumHeavy font-heavy text-2xl'>
          {nickName}님의 작업일지 {'>'} {cropName} {'>'} 작업일지 작성
        </p>

        {loading ? (
          <div className='flex justify-center items-center h-56'>
            <div className='border-t-4 border-[#698A54] border-solid w-16 h-16 rounded-full animate-spin'></div>
          </div>
        ) : (
          <div className='flex flex-col gap-5 w-full h-3/5 overflow-y-auto my-7'>
            {details.length === 0 ? (
              <p>추천 작업이 없습니다.</p>
            ) : (
              details.map((detail, index) => (
                <TodoDetail
                  key={index}
                  detail={detail}
                  onRemove={() => removeDetail(index)}
                />
              ))
            )}
          </div>
        )}

        <div className='flex flex-col justify-center items-center'>
          <div className='flex w-full items-center gap-3'>
            <input
              type='text'
              value={inputValue}
              onChange={(e) => setInputValue(e.target.value)}
              onKeyPress={handleKeyPress}
              placeholder='추가로 할 작업을 선택해주세요.'
              className='flex-1 h-12 rounded-lg bg-gray-100 pl-3'
            />
            <button
              onClick={addDetail}
              className='px-4 py-2 rounded-md font-extrabold bg-[#698A54] text-white'
            >
              추가
            </button>
          </div>
          <button
            onClick={handleComplete}
            className='mt-5 rounded-md font-extrabold w-24 h-9 bg-[#698A54] text-white'
          >
            완료
          </button>
        </div>
      </div>
    </div>
  );
};

export default DetailWrite;
