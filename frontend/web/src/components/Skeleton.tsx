const Skeleton = ({ className }: { className?: string }) => (
  <div
    className={` bg-gray-200 rounded-md animate-skeleton ${className}`}
  ></div>
);
export default Skeleton;
