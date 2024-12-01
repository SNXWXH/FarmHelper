import Image from 'next/image';
import { useRouter } from 'next/navigation';

export default async function TodayCropModal({
  cropName,
}: {
  cropName: string | null;
}) {
  const router = useRouter();
  const encodedCropName = encodeURIComponent(cropName);

  const todayCropData = await (
    await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/api/getDescription?cropName=${encodedCropName}`
    )
  ).json();

  const cropDetails = JSON.parse(todayCropData[0].description);

  const plantingTime = cropDetails['Planting season'];
  const harvestTime = cropDetails['Harvest season'];
  const description = cropDetails['Description'];

  return (
    <>
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
            <p className='font-nanumHeavy font-heavy text-3xl'>{cropName}</p>
            <p className='font-nanumHeavy font-heavy mt-4'>
              심는 시기: {plantingTime}
            </p>
            <p className='font-nanumHeavy font-heavy'>
              수확 시기: {harvestTime}
            </p>
            <p className='font-extrabold mt-6 whitespace-break-spaces'>
              {description}
            </p>
          </div>
        </div>
      </div>
    </>
  );
}
