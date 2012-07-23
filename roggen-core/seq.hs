-- Effectful computations
import Data.Traversable
import Control.Monad
import Control.Concurrent

newtype Seq a = Seq { runSeq:: ((a -> IO()) -> IO()) }

infixl 1 >>>	

(>>>) = runSeq

runSeq' seq = seq >>> drain

drain :: a -> IO ()
drain = \_ -> return ()

discard :: IO a -> IO ()
discard = fmap (const ()) 

nil :: Seq a
nil = Seq drain

cons :: a -> Seq a
cons a = Seq $ \ioa -> ioa a

fromMaybe :: Maybe a -> Seq a
fromMaybe (Just x) = cons x
fromMaybe _        = nil

fromList :: [a] -> Seq a
fromList as = Seq $ \ioa -> discard $ (traverse ioa as)

async :: a -> Seq a
async a = asyncWith (discard.forkIO)

asyncWith :: a -> Seq a
asyncWith executor a = Seq $ \ioa -> executor (ioa a)

map_ :: Monad m => (a -> b) -> a -> m b
map_ f = return.f

filter_ :: MonadPlus m => (a -> Bool) -> a -> m a
filter_ f = \a -> if f a then return a else mzero

-- splits an effect pipeline with a generic payload
concatMap_ :: (a -> [b]) -> a -> Seq b
concatMap_ f = fromList.f

-- splits the effect pipeline with a list payload
concat_ :: [a] -> Seq a
concat_ = fromList

-- Adds an effect to the effect pipeline
each_ :: (a -> IO()) -> a -> Seq a
each_ ioa1 = \a -> Seq $ \ioa2 -> ioa1 a >> ioa2 a

instance Monad Seq where
  seqa >>= seqb_ =  Seq $ \iob -> (seqa >>> \a -> seqb_ a >>> iob)   
  return = cons

instance MonadPlus Seq where
  mzero = nil
  mplus seqa1 seqa2 = Seq $ \ioa -> (seqa1 >>> ioa) >> (seqa2 >>> ioa)

