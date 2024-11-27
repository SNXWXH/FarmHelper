import { NextRequest, NextResponse } from 'next/server';

export async function GET(req: NextRequest) {
  const { searchParams } = req.nextUrl;
  const ip = searchParams.get('ip');

  try {
    const response = await fetch(
      `${process.env.SERVER_BASE_URL}/api/weather/${ip}`,
      {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
      }
    );

    const data = await response.json();

    return NextResponse.json(data, { status: 200 });
  } catch (error) {
    throw new Error(error, 'Server-Failed to fetch currtentWeather Data');
  }
}
