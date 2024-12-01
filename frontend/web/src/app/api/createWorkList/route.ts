import { NextRequest, NextResponse } from 'next/server';

export async function GET(req: NextRequest) {
  const { searchParams } = req.nextUrl;
  const userId = searchParams.get('userId');
  const cropNameParams = searchParams.get('cropName');
  const cropName = decodeURIComponent(cropNameParams);
  const cropDate = searchParams.get('cropDate');
  const imageUrl = searchParams.get('imageUrl');

  try {
    const response = await fetch(
      `${process.env.SERVER_BASE_URL}/api/crop/create`,
      {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, cropName, cropDate, imageUrl }),
      }
    );

    const data = await response.json();

    return NextResponse.json(data, { status: 200 });
  } catch (error) {
    throw new Error(error, 'Server-Failed to fetch login Data');
  }
}