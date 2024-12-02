'use client';

import { ChangeEvent, useState } from 'react';
import { useSession } from 'next-auth/react';
import { uploadImage } from '@/utils/uploadImage';
import { useRouter } from 'next/navigation';

export default function CreateLog() {
  const [fileName, setFileName] = useState('');
  const [fileInput, setFileInput] = useState<File | null>(null);
  const [date, setDate] = useState('');
  const [crop, setCrop] = useState('');
  const router = useRouter();

  const handleFileChange = async (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      setFileName(file.name);
      setFileInput(file);
    } else {
      setFileName('');
    }
  };

  const handleDateChange = (event: ChangeEvent<HTMLInputElement>) => {
    const selectedDate = event.target.value;
    setDate(selectedDate);
  };

  const handleCropChange = (event: ChangeEvent<HTMLInputElement>) => {
    const selectedCrop = event.target.value;
    setCrop(selectedCrop);
  };

  const { data: session } = useSession();

  const handleSubmit = async () => {
    if (!session?.user.uid) {
      alert('로그인이 필요합니다.');
      return;
    }

    if (fileInput) {
      try {
        const downloadURL = await uploadImage(fileInput, session.user.uid);

        const cropName = crop;
        const cropDate = date;

        const response = await fetch('/api/createWorkList', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            userId: session?.user.uid,
            cropName,
            cropDate,
            imageUrl: downloadURL,
          }),
        });

        if (response.ok) {
          router.push('/workLog');
        } else {
          const errorData = await response.json();
          alert(`Error: ${errorData.error}`);
        }
      } catch (error) {
        console.error('Error uploading image or sending data:', error.message);
      }
    }
  };

  return (
    <>
      <div className='flex h-screen justify-center'>
        <div className='flex flex-col justify-center items-center w-3/5'>
          <p className='font-nanumHeavy font-heavy text-2xl'>작업일지 생성</p>
          <p className='text-xl mt-6'>심을 작물과 날짜를 입력해주세요!</p>
          <div className='w-1/2 justify-center'>
            <div className='flex mt-6 items-center'>
              <p className='w-1/4 font-extrabold'>작물</p>
              <input
                type='text'
                placeholder='작물을 입력해주세요.'
                className='ml-auto w-2/3 h-12 rounded-lg bg-gray-100 pl-3 text-sm'
                value={crop}
                onChange={handleCropChange}
              />
            </div>
            <div className='flex mt-4 items-center'>
              <p className='w-1/4 font-extrabold'>날짜</p>
              <input
                type='date'
                value={date}
                onChange={handleDateChange}
                className='ml-auto w-2/3 h-12 rounded-lg bg-gray-100 pl-3 text-sm'
              />
            </div>
            <div className='flex mt-6 items-center'>
              <p className='w-1/4 font-extrabold'>커버이미지</p>
              <label
                htmlFor='file-upload'
                className='px-4 py-2 text-sm text-green-500 border border-green-500 rounded-md cursor-pointer hover:bg-green-100 ml-auto'
              >
                이미지 첨부
              </label>
              <input
                id='file-upload'
                type='file'
                className='hidden'
                onChange={handleFileChange}
              />
            </div>
            {fileName && (
              <p className='mt-2 text-right text-sm text-gray-500'>
                {fileName}
              </p>
            )}
          </div>
          <button
            className='mt-10 rounded-md font-extrabold w-24 h-9 bg-[#698A54] text-white'
            onClick={handleSubmit}
          >
            완료
          </button>
        </div>
      </div>
    </>
  );
}
