'use client';

import { useState } from 'react';

export default function CreateNickname() {
  const [nickname, setNickname] = useState('');

  const handleSubmit = () => {
    if (nickname.trim() === '') {
      alert('닉네임은 공백일 수 없습니다.');
    } else {
      console.log(nickname);
    }
  };

  return (
    <>
      <div className='flex justify-center items-center h-screen font-nanumHeavy font-heavy'>
        <div className='flex flex-col justify-center items-center gap-7 bg-[#F2FFE0] w-3/5 h-96 p-6 rounded-2xl'>
          <p className='relative text-3xl'>닉네임을 설정해주세요</p>
          <input
            type='text'
            placeholder='닉네임을 입력해주세요'
            className='w-2/3 h-12 rounded-lg bg-gray-100 pl-3 text-sm'
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
          />
          <button
            className='mt-10 rounded-md font-extrabold w-24 h-11 bg-[#698A54] text-white'
            onClick={handleSubmit}
          >
            완료
          </button>
        </div>
      </div>
    </>
  );
}