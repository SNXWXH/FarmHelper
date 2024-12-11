'use client';

import { useSearchParams } from 'next/navigation';
import Modal from '@/components/Modal';
import TodayCropModal from '@/components/TodayCropModal';

export default function TodayCrop() {
  const searchParams = useSearchParams();
  const cropName = searchParams.get('cropName');

  return (
    <Modal>
      <TodayCropModal cropName={cropName} />
    </Modal>
  );
}
