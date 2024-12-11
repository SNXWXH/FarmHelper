import NextAuth, { User } from 'next-auth';

import CredentialsProvider from 'next-auth/providers/credentials';
interface NewUser extends User {
  uid: string;
}
const handler = NextAuth({
  providers: [
    CredentialsProvider({
      name: 'Firebase Credentials',
      credentials: {
        uid: { type: 'text' },
        email: { type: 'text' },
        name: { type: 'text' },
      },
      async authorize(credentials) {
        const { uid, email, name = '' } = credentials;

        if (!uid || !email) {
          throw new Error('잘못된 인증 정보입니다.');
        }

        return { id: uid, uid, email, name };
      },
    }),
  ],
  session: {
    strategy: 'jwt',
  },
  callbacks: {
    async jwt({ token, user }) {
      if (user) {
        token.uid = user.id;
      }
      return token;
    },
    async session({ session, token }) {
      session.user.uid = token.uid;
      return session;
    },
  },
});

export { handler as GET, handler as POST };
