import CropList from '@/components/CropList';
import CropListCard from '@/components/CropListCard';

export default function WorkLog() {
  return (
    <>
      <div className='flex flex-col items-center h-screen w-full pt-14'>
        <div className='w-3/5'>
          <p className='mt-14 mb-10 font-nanumHeavy font-heavy text-2xl'>
            이설아님의 작업일지
          </p>

          <div className='grid grid-cols-3 gap-10'>
            <CropListCard cropName='옥수수' date='2024.10.31' />
            <CropListCard cropName='옥수수' date='2024.10.31' />
            <CropListCard cropName='옥수수' date='2024.10.31' />
            <CropListCard cropName='옥수수' date='2024.10.31' />
            <CropListCard cropName='옥수수' date='2024.10.31' />
            <CropListCard cropName='옥수수' date='2024.10.31' />
            <CropListCard cropName='옥수수' date='2024.10.31' />
            <CropListCard cropName='옥수수' date='2024.10.31' />
          </div>

          {/* <div className='flex flex-col gap-9 mb-10'>
            <CropList cropName='옥수수' date='2024.10.31' />
            <CropList cropName='옥수수' date='2024.10.31' />
            <CropList cropName='옥수수' date='2024.10.31' />
            <CropList cropName='옥수수' date='2024.10.31' />
            <CropList cropName='옥수수' date='2024.10.31' />
            <CropList cropName='옥수수' date='2024.10.31' />
            <CropList cropName='옥수수' date='2024.10.31' />
          </div> */}
        </div>
      </div>
    </>
  );
}
