export default async function MainWeather() {
  const weather: Response = await fetch(
    `${process.env.BASE_URL}/api/currentWeather`
  );
  const weatherData = await weather.json();

  return (
    <>
      <div className='mt-8 h-40 w-full flex justify-center bg-[#F2FFE0] rounded-2xl'>
        <div className='flex justify-center items-center w-1/2 font-nanumHeavy font-heavy'>
          <p className='text-6xl'>{weatherData.temperature}</p>
          <p className='text-3xl h-16'>°</p>
          <div className='ml-2'>
            <p className='text-2xl'>{weatherData.description}</p>
          </div>
        </div>
        <div className='flex flex-col justify-center items-center w-1/2 gap-3 mr-8'>
          <div className='flex gap-8'>
            <div className='flex w-32 items-center'>
              <p className='mr-2 font-extrabold text-[#8B8E88]'>습도</p>
              <p className='font-nanumHeavy font-heavy text-sm'>
                {weatherData.humidity}
              </p>
            </div>
            <div className='flex w-32 items-center'>
              <p className='mr-2 font-extrabold text-[#8B8E88]'>체감온도</p>
              <p className='font-nanumHeavy font-heavy text-sm'>
                {weatherData.feels_like}
              </p>
            </div>
          </div>
          <div className='flex gap-8'>
            <div className='flex w-32 items-center'>
              <p className='mr-2 font-extrabold text-[#8B8E88]'>바람</p>
              <p className='font-nanumHeavy font-heavy text-sm'>
                {weatherData.wind_speed}
              </p>
            </div>
            <div className='flex w-32 items-center'>
              <p className='mr-2 font-extrabold text-[#8B8E88]'>일출</p>
              <p className='font-nanumHeavy font-heavy text-sm'>
                {weatherData.sunrise}
              </p>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}