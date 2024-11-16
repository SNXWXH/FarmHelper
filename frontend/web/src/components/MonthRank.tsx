export default function MonthRank() {
  const ranks = [];
  for (let idx = 1; idx <= 5; idx++) {
    ranks.push(
      <div key={idx} className='flex m-2'>
        <span className='w-1/4 text-center font-extrabold text-xl'>{idx}</span>
        <span className='flex justify-center items-center w-3/4 text-center text-xl'>
          감자
        </span>
      </div>
    );
  }

  return (
    <>
      <div className='flex mt-8'>
        <div className='w-1/2 flex flex-col'>{ranks}</div>
        <div className='w-1/2 flex flex-col'>{ranks}</div>
      </div>
    </>
  );
}
