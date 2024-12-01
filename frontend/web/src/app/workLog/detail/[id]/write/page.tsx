'use client';

import { useState, useEffect } from 'react';
import { useSearchParams } from 'next/navigation';
import TodoDetail from '@/components/TodoDetail';

export default function DetailWrite() {
  const [details, setDetails] = useState<string[]>([]);
  const [inputValue, setInputValue] = useState<string>('');
  const searchParams = useSearchParams();

  const userId = searchParams.get('userId');
  const cropId = searchParams.get('cropId');

  useEffect(() => {
    const fetchAIData = async () => {
      try {
        const response = await fetch(
          `${process.env.NEXT_PUBLIC_BASE_URL}/api/getAIWorkLog?userId=${userId}&cropId=${cropId}`
        );
        const data = await response.json();
        setDetails(data.recommendations);
      } catch (error) {
        console.error('Error fetching AI data:', error);
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

  const handleComplete = async () => {};

  return (
    <div className='flex flex-col items-center h-screen w-full pt-14'>
      <div className='w-3/5'>
        <p className='mt-14 font-nanumHeavy font-heavy text-2xl'>
          이설아님의 작업일지 {'>'} 감자 {'>'} 작업일지 작성
        </p>
        <div className='flex flex-col gap-5 w-full h-3/5 overflow-y-auto my-7'>
          {details.map((detail, index) => (
            <TodoDetail
              key={index}
              detail={detail}
              onRemove={() => removeDetail(index)}
            />
          ))}
        </div>
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
}
