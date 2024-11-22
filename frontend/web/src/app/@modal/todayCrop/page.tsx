'use client';

import Modal from '@/components/Modal';
import { useRouter } from 'next/navigation';
import Image from 'next/image';

export default function TodayCrop() {
  const router = useRouter();

  return (
    <>
      <Modal>
        <div className='flex flex-col bg-[#F2FFE0] w-3/5 h-80 p-6 rounded-2xl'>
          <button
            onClick={() => {
              router.back();
            }}
            className='flex justify-end items-end font-nanumHeavy font-heavy'
          >
            X
          </button>
          <div className='flex justify-center items-center w-full h-full gap-20'>
            <div className='flex justify-center items-center w-60 h-60 bg-white rounded-lg'>
              <Image src='/temporaryImg.png' alt='img' width={200} height={0} />
            </div>
            <div className='w-3/5 h-60'>
              <p className='font-nanumHeavy font-heavy text-3xl'>고구마</p>
              <p className='font-nanumHeavy font-heavy mt-4'>
                심는 시기: 4월 말 ~ 5월 초 (날씨가 따뜻해질 때)
              </p>
              <p className='font-nanumHeavy font-heavy'>
                수확 시기: 9월 ~ 10월 (심은 후 90일 ~ 120일 후)
              </p>
              <p className='font-extrabold mt-6 whitespace-break-spaces'>
                고구마는 섬유질, 비타민 A, C, 칼륨 등이 풍부해 건강에 좋은
                식품으로, 면역력 강화와 소화에 도움을 줄 수 있어요. 또한,
                병충해에 강해 관리가 비교적 쉬운 편이고, 수확 후 잘 건조하면 약
                6개월까지 보관할 수 있어요.
              </p>
            </div>
          </div>
        </div>
      </Modal>
    </>
  );
}
