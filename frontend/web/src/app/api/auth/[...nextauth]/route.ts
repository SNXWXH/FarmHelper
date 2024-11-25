import NextAuth from 'next-auth';
import GoogleProvider from 'next-auth/providers/google';

const handler = NextAuth({
  providers: [
    GoogleProvider({
      clientId: process.env.GOOGLE_CLIENT_ID,
      clientSecret: process.env.GOOGLE_CLIENT_SECRET,
    }),
  ],
  callbacks: {
    async session({ session, token }) {
      session.user.uuid = token.sub;
      return session;
    },
    async jwt({ token, account }) {
      if (account?.provider === 'google') {
        token.sub = account.providerAccountId;
      }
      return token;
    },
  },
});

export { handler as GET, handler as POST };
