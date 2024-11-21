import DeatilList from '@/components/DeatilList';

export default function Detail() {
  const data1 = [
    [
      '밭 준비하기: 감자는 배수가 잘되는 토양을 좋아하니, 심기 전에 흙을 부드럽게 고르고 필요하면 모래를 섞어 배수성을 높여주세요.',
      '밭 준비하기: 감자는 배수가 잘되는 토양을 좋아하니, 심기 전에 흙을 부드럽게 고르고 필요하면 모래를 섞어 배수성을 높여주세요.',
      '밭 준비하기: 감자는 배수가 잘되는 토양을 좋아하니, 심기 전에 흙을 부드럽게 고르고 필요하면 모래를 섞어 배수성을 높여주세요.',
      '밭 준비하기: 감자는 배수가 잘되는 토양을 좋아하니, 심기 전에 흙을 부드럽게 고르고 필요하면 모래를 섞어 배수성을 높여주세요.',
      '밭 준비하기: 감자는 배수가 잘되는 토양을 좋아하니, 심기 전에 흙을 부드럽게 고르고 필요하면 모래를 섞어 배수성을 높여주세요.',
      '밭 준비하기: 감자는 배수가 잘되는 토양을 좋아하니, 심기 전에 흙을 부드럽게 고르고 필요하면 모래를 섞어 배수성을 높여주세요.',
    ],
    [
      '밭 준비하기: 감자는 배수가 잘되는 토양을 좋아하니, 심기 전에 흙을 부드럽게 고르고 필요하면 모래를 섞어 배수성을 높여주세요.',
      '밭 준비하기: 감자는 배수가 잘되는 토양을 좋아하니, 심기 전에 흙을 부드럽게 고르고 필요하면 모래를 섞어 배수성을 높여주세요.',
      '밭 준비하기: 감자는 배수가 잘되는 토양을 좋아하니, 심기 전에 흙을 부드럽게 고르고 필요하면 모래를 섞어 배수성을 높여주세요.',
    ],
  ];

  return (
    <>
      <div className='flex flex-col items-center h-screen w-full pt-14'>
        <div className='w-3/5'>
          <p className='mt-14 font-nanumHeavy font-heavy text-2xl'>
            이설아님의 작업일지 {'>'} 감자
          </p>
          <div className='mt-5'>
            <DeatilList />
          </div>
          <div className='w-full h-0.5 bg-black my-6'> </div>
          <div className='flex flex-col'>
            <div className='flex justify-end'>
              <button className='w-28 h-9 bg-[#B3D99C] text-white rounded-xl mb-6'>
                내림차순
              </button>
            </div>
            <div className='flex flex-col mb-8'>
              <DeatilList data={data1} />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
