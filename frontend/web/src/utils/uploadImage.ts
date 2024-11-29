import { ref, uploadBytes, getDownloadURL } from 'firebase/storage';
import { storage } from '../../firebase-config';

const uploadImage = async (file, userID) => {
  console.log('🚀  userID:', userID);

  if (!file) throw new Error('No file selected.');
  if (!userID) throw new Error('User ID not found.');

  try {
    // 파일명에서 확장자 제거
    const fileNameWithoutExtension = file.name
      .split('.')
      .slice(0, -1)
      .join('.');
    const timestamp = new Date().toISOString().replace(/[:.]/g, '-'); // 파일명에 사용할 타임스탬프
    const filePath = `${userID}/${fileNameWithoutExtension}-${timestamp}.jpg`; // 사용자 ID 폴더 아래에 파일 경로 생성

    const fileRef = ref(storage, filePath);
    await uploadBytes(fileRef, file);

    const downloadURL = await getDownloadURL(fileRef); //
    return downloadURL;
  } catch (error) {
    console.error('Error uploading file:', error);
    throw new Error('File upload failed.');
  }
};

export { uploadImage };
