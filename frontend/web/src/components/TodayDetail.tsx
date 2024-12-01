import { useRouter } from 'next/navigation';

export default function TodayDetail({
  userId,
  cropId,
}: {
  userId: string;
  cropId: string;
}) {
  const router = useRouter();

  const handleAIClick = () => {
    router.push(
      `/workLog/detail/${cropId}/write?userId=${userId}&cropId=${cropId}`
    );
  };

  return (
    <>
      <div className='flex items-center justify-between'>
        <div className='flex'>
          <p className='text-xl font-extrabold'>5일차: 2024.10.24(목)</p>
          <p className='ml-4 font-extrabold'>날씨: 맑음 온도: 19 ℃</p>
        </div>
        <div className='flex'>
          <button className='flex items-center justify-center w-20 h-10 bg-[#698A54] rounded-xl text-white mr-3'>
            수정
          </button>
          <button className='flex items-center justify-center w-20 h-10 bg-[#698A54] rounded-xl text-white'>
            삭제
          </button>
        </div>
      </div>
      <div className='w-full bg-[#F2FAE7] rounded-2xl p-7 my-6'>
        <div className='flex flex-col justify-center items-center h-full gap-5 p-8'>
          <p className='text-2xl'>오늘의 할 일이 비어있습니다.</p>
          <button
            onClick={handleAIClick}
            className='flex justify-center items-center w-28 h-12 bg-[#698A54] rounded-xl text-lg text-white'
          >
            AI 추천
          </button>
        </div>
      </div>
    </>
  );
}
