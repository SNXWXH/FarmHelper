import { NextRequest, NextResponse } from 'next/server';

export async function GET(req: NextRequest) {
  const { searchParams } = req.nextUrl;
  const userId = searchParams.get('userId');
  const cropId = searchParams.get('cropId');

  try {
    const response = await fetch(
      `${process.env.SERVER_BASE_URL}/api/work/recommend/create`,
      {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, cropId }),
      }
    );

    const data = await response.json();

    return NextResponse.json(data, { status: 200 });
  } catch (error) {
    throw new Error('Server-Failed to fetch getAiWorkLog Data');
  }
}
