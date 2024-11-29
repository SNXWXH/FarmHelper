import { NextRequest, NextResponse } from 'next/server';

export async function GET(req: NextRequest) {
  const { searchParams } = req.nextUrl;
  const userId = searchParams.get('userId');
  const nicknameParams = searchParams.get('nickname');
  const nickname = decodeURIComponent(nicknameParams);

  try {
    const response = await fetch(
      `${process.env.SERVER_BASE_URL}/api/login/nickname`,
      {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, nickname }),
      }
    );

    // const data = await response.json();
    const responseText = await response.text();
    console.log('Server Response:', responseText);

    return NextResponse.json(responseText, { status: 200 });
  } catch (error) {
    throw new Error(error, 'Server-Failed to fetch login Data');
  }
}
