import { NextRequest, NextResponse } from 'next/server';

export async function GET(req: NextRequest) {
  const { searchParams } = req.nextUrl;
  const userId = searchParams.get('userId');
  const cropNameParams = searchParams.get('cropName');
  const cropName = decodeURIComponent(cropNameParams);
  const cropDate = searchParams.get('cropDate');
  const imageUrl = decodeURIComponent(searchParams.get('imageUrl'));

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

    if (data.isOK) {
      return NextResponse.json(
        { message: '작업 일지가 성공적으로 생성되었습니다.' },
        { status: 200 }
      );
    } else {
      return NextResponse.json(
        { error: '작업 일지 생성에 실패했습니다.' },
        { status: 400 }
      );
    }
  } catch (error) {
    throw new Error(error, 'Server-Failed to fetch login Data');
  }
}
