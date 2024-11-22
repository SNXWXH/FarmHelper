import { ReactNode } from 'react';

export default function Modal({ children }: { children: ReactNode }) {
  return (
    <>
      {/* <div className='fixed inset-0 bg-black bg-opacity-35 z-1 flex items-center justify-center'> */}
      <div className='flex fixed justify-center items-center w-full h-full bg-black bg-opacity-35'>
        {children}
      </div>
      {/* </div> */}
    </>
  );
}
