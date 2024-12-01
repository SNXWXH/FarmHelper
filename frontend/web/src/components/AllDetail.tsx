import Link from 'next/link';

export default function AllDetail({ data = [] }: { data?: string[][] }) {
  return (
    <>
      <>
        {data.map((list, idx) => (
          <div key={idx}>
            <div className='flex items-center'>
              <p className='text-xl font-extrabold'>
                {idx + 1}일차: 2024.10.24(목)
              </p>
              <p className='ml-4 font-extrabold'>날씨: 맑음 온도: 19 ℃</p>
            </div>
            <div className='w-full bg-[#F2FAE7] rounded-2xl p-7 my-6'>
              <div className='flex flex-col'>
                {list.map((todo, idx) => (
                  <p className='my-1' key={idx}>
                    • {todo}
                  </p>
                ))}
              </div>
            </div>
          </div>
        ))}
      </>
    </>
  );
}
